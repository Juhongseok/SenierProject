package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.compositid.UserMapId;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.SmallSubjectRepository;
import com.jhs.seniorProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;
    private final UserRepository userRepository;

    /**
     * 새로운 지도 생성
     * @param name
     * @param user
     * @return
     */
    public Map createMap(String name, User user) {
        //TODO Entity -> DTO 변경
        Map map = new Map(name, user.getId());
        User findUser = userRepository.findById(user.getId()).get();
        UserMap userMap = new UserMap(new UserMapId(findUser.getId(), map.getId()), findUser, map);
        SmallSubject subject1 = new SmallSubject("카페", map, findUser.getName());
        SmallSubject subject2 = new SmallSubject("식당", map, findUser.getName());

        map.getUserMaps().add(userMap);
        map.getSmallSubjects().add(subject1);
        map.getSmallSubjects().add(subject2);

        return mapRepository.save(map);
    }

    /**
     * 기존 지도 추가
     * @param userId 최초 생성한 사용자 아이디
     * @param password 지도 비밀번호
     * @param user 지도 추가 시도한 사용자 --> login user
     * @return
     * @throws NoSuchMapException
     */
    public Map addMap(String userId, String password, User user) throws NoSuchMapException {
        //TODO Entity -> DTO 변경
        Map findMap = mapRepository.findByCreatedByAndPassword(userId, password)
                .orElseThrow(() -> new NoSuchMapException("만든사람 혹은 비밀번호가 잘못 되었습니다."));
        User findUser = userRepository.findById(user.getId()).get();
        UserMap userMap = new UserMap(new UserMapId(findUser.getId(), findMap.getId()), findUser, findMap);
        userMap.canNotVisibility();
        findMap.getUserMaps().add(userMap);
        return findMap;
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

    /**
     * 지도 정보 확인 시 사용
     * @param mapId 아이디 가지고 지도 검색
     * @param userId 최초 만든 사용자인지 확인 --> 해당 지도 추가한 사용자 목록 검색
     * @return
     * @throws NoSuchMapException
     */
    @Transactional(readOnly = true)
    public Map getMap(Long mapId, String userId) throws NoSuchMapException {
        //TODO Entity -> DTO 변경
        Map findMap = mapRepository.findById(mapId)
                .orElseThrow(() -> new NoSuchMapException("지도를 찾을 수 없습니다."));
        if (findMap.getCreatedBy().equals(userId)) {
            for (UserMap userMap : findMap.getUserMaps()) {
                userMap.getMap();
            }
        }
        return findMap;
    }

    /**
     * @param user
     * @return 사용자가 가진 지도 리스트
     */
    @Transactional(readOnly = true)
    public List<Map> getMaps(User user) {
        //TODO Entity -> DTO 변경
        return mapRepository.findAll(user.getId());
    }
}
