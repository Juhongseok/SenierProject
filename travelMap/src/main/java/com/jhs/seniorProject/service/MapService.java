package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.compositid.UserMapId;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.SmallSubjectRepository;
import com.jhs.seniorProject.repository.UserMapRepository;
import com.jhs.seniorProject.repository.UserRepository;
import com.jhs.seniorProject.service.requestform.AddMapDto;
import com.jhs.seniorProject.service.requestform.CreateMapDto;
import com.jhs.seniorProject.service.responseform.MapInfo;
import com.jhs.seniorProject.service.responseform.SmallSubjectInfo;
import com.jhs.seniorProject.service.responseform.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;
    private final UserRepository userRepository;
    private final SmallSubjectRepository smallSubjectRepository;
    private final UserMapRepository userMapRepository;

    /**
     * 새로운 지도 생성
     *
     * @param createMapDto
     * @return
     */
    public Long createMap(CreateMapDto createMapDto) {
        Map map = new Map(createMapDto.getMapName(), createMapDto.getUserId());
        User findUser = userRepository.findById(createMapDto.getUserId()).get();

        UserMap userMap = new UserMap(new UserMapId(findUser.getId(), map.getId()), findUser, map);
        SmallSubject subject1 = new SmallSubject("카페", map, findUser.getName());
        SmallSubject subject2 = new SmallSubject("식당", map, findUser.getName());
        SmallSubject subject3 = new SmallSubject("숙소", map, findUser.getName());
        SmallSubject subject4 = new SmallSubject("놀거리", map, findUser.getName());

        //연관관계 같이 등록 되도록 설정
        map.getUserMaps().add(userMap);
        map.getSmallSubjects().add(subject1);
        map.getSmallSubjects().add(subject2);
        map.getSmallSubjects().add(subject3);
        map.getSmallSubjects().add(subject4);

        Map saveMap = mapRepository.save(map);
        return saveMap.getId();
    }

    /**
     * 기존 지도 추가
     *
     * @param addMapDto
     * @return
     * @throws NoSuchMapException
     */
    public Long addMap(AddMapDto addMapDto) throws NoSuchMapException {
        Map findMap = findMap(addMapDto);
        User findUser = userRepository.findById(addMapDto.getAddUserId()).get();

        UserMap userMap = new UserMap(new UserMapId(findUser.getId(), findMap.getId()), findUser, findMap);
        userMap.canNotVisibility();

        //연관관계 같이 등록 되도록 설정
        findMap.getUserMaps().add(userMap);
        return findMap.getId();
    }

    /**
     * 지도 정보 확인 시 사용
     *
     * @param mapId  아이디 가지고 지도 검색
     * @param userId 최초 만든 사용자인지 확인 --> 해당 지도 추가한 사용자 목록 검색
     * @return
     * @throws NoSuchMapException
     */
    @Transactional(readOnly = true)
    public MapInfo getMapInfo(Long mapId, String userId, Pageable subjectPageable, Pageable userPageable) throws NoSuchMapException {
        Map findMap = mapRepository.findById(mapId)
                .orElseThrow(() -> new NoSuchMapException("지도를 찾을 수 없습니다."));

        Page<SmallSubjectInfo> subject = smallSubjectRepository.findByMapId(mapId, subjectPageable)
                .map(s -> new SmallSubjectInfo(s.getId(), s.getSubjectName()));

        MapInfo mapInfo = MapInfo.builder()
                .mapId(findMap.getId())
                .mapName(findMap.getName())
                .password(findMap.getPassword())
                .smallSubjects(subject)
                .userInfos(Page.empty())
                .build();
        if (findMap.getCreatedBy().equals(userId)) {
            mapInfo.setUserInfos(
                    userMapRepository.findByIdMapId(mapId, userPageable)
                            .map(um -> new UserInfo(um.getId().getUserId(), um.getVisibility()))
            );
        }
        return mapInfo;
    }

    /**
     * 지도 접근 권한 부여
     *
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

    @Transactional(readOnly = true)
    public List<SmallSubjectInfo> getSmallSubjects(Long mapId) {
        return mapRepository.findSmallSubject(mapId).stream()
                .map(subject -> new SmallSubjectInfo(subject.getId(), subject.getSubjectName()))
                .collect(toList());
    }

    private Map findMap(AddMapDto addMapDto) throws NoSuchMapException {
        return mapRepository.findByCreatedByAndPassword(addMapDto.getCreateUserId(), addMapDto.getPassword())
                .orElseThrow(() -> new NoSuchMapException("만든사람 혹은 비밀번호가 잘못 되었습니다."));
    }
}
