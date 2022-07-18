package com.jhs.seniorProject.service;

import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.compositid.UserMapId;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.UserRepository;
import com.jhs.seniorProject.service.requestform.AddMapDto;
import com.jhs.seniorProject.service.requestform.CreateMapDto;
import com.jhs.seniorProject.service.responseform.MapInfoAdmin;
import com.jhs.seniorProject.service.responseform.MapInfoResponse;
import com.jhs.seniorProject.service.responseform.MapInfoUser;
import com.jhs.seniorProject.service.responseform.MapList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 지도 생성
     * @param createMapDto
     * @return
     */
    public String createMap(CreateMapDto createMapDto) {
        Map map = new Map(createMapDto.getMapName(), createMapDto.getUserId());
        User findUser = userRepository.findById(createMapDto.getUserId()).get();

        UserMap userMap = new UserMap(new UserMapId(findUser.getId(), map.getId()), findUser, map);
        SmallSubject subject1 = new SmallSubject("카페", map, findUser.getName());
        SmallSubject subject2 = new SmallSubject("식당", map, findUser.getName());

        //연관관계 같이 등록 되도록 설정
        map.getUserMaps().add(userMap);
        map.getSmallSubjects().add(subject1);
        map.getSmallSubjects().add(subject2);

        Map saveMap = mapRepository.save(map);
        return saveMap.getName();
    }

    /**
     *
     * @param addMapDto
     * @return
     * @throws NoSuchMapException
     */
    public String addMap(AddMapDto addMapDto) throws NoSuchMapException {
        Map findMap = findMap(addMapDto);
        User findUser = userRepository.findById(addMapDto.getAddUserId()).get();

        UserMap userMap = new UserMap(new UserMapId(findUser.getId(), findMap.getId()), findUser, findMap);
        userMap.canNotVisibility();

        //연관관계 같이 등록 되도록 설정
        findMap.getUserMaps().add(userMap);
        return findMap.getName();
    }

    /**
     * @param user
     * @return 사용자가 가진 지도 리스트
     */
    @Transactional(readOnly = true)
    public List<MapList> getMaps(LoginUser user) {
        return mapRepository.findAll(user.getId()).stream()
                .map(MapList::from)
                .collect(toList());
    }

    /**
     * 지도 정보 확인 시 사용
     * @param mapId 아이디 가지고 지도 검색
     * @param userId 최초 만든 사용자인지 확인 --> 해당 지도 추가한 사용자 목록 검색
     * @return
     * @throws NoSuchMapException
     */
    @Transactional(readOnly = true)
    public MapInfoResponse getMap(Long mapId, String userId) throws NoSuchMapException {
        Map findMap = mapRepository.findById(mapId)
                .orElseThrow(() -> new NoSuchMapException("지도를 찾을 수 없습니다."));

        if (findMap.getCreatedBy().equals(userId)) {
            MapInfoAdmin mapInfo = MapInfoAdmin.builder()
                    .mapName(findMap.getName())
                    .password(findMap.getPassword())
                    .build();
            for (UserMap userMap : findMap.getUserMaps()) {
                mapInfo.addUserInfo(userMap);
            }
            return mapInfo;
        }
        return MapInfoUser.builder()
                .mapName(findMap.getName())
                .password(findMap.getPassword())
                .build();
    }

    /**
     * 지도 접근 권한 부여
     * @param mapId
     * @param userId
     * @throws NoSuchMapException
     */
    public void accessMap(Long mapId, String userId) throws NoSuchMapException {
        Map map = mapRepository.findById(mapId)
                .orElseThrow(() -> new NoSuchMapException("만든사람 아이디가 잘못 되었습니다."));
        List<UserMap> userMaps = map.getUserMaps();
        for (UserMap userMap : userMaps) {
            if (userMap.getId().getUserId().equals(userId)) {
                userMap.canVisibility();
                break;
            }
        }
    }

    private Map findMap(AddMapDto addMapDto) throws NoSuchMapException {
        return mapRepository.findByCreatedByAndPassword(addMapDto.getCreateUserId(), addMapDto.getPassword())
                .orElseThrow(() -> new NoSuchMapException("만든사람 혹은 비밀번호가 잘못 되었습니다."));
    }
}
