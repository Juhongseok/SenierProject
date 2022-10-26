package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.compositid.UserMapId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MapRepositoryTest {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private UserRepository userRepository;

    /*@Test
    @DisplayName("지도 생성")
    void createNewMap(){
        //given
        User userA = userRepository.findById("userA").get();
        Map mapA = new Map("mapA", userA.getName());
        UserMap userMap = new UserMap(new UserMapId(userA.getId(), mapA.getId()), userA, mapA);
        mapA.getUserMaps().add(userMap);

        //when
        Map savedMap = mapRepository.save(mapA);

        //then
        assertThat(savedMap).isEqualTo(mapA);
        assertThat(userA).isEqualTo(userMap.getUser());
        assertThat(savedMap).isEqualTo(userMap.getMap());
        assertThat(savedMap.getId()).isEqualTo(userMap.getId().getMapId());
        assertThat(userA.getId()).isEqualTo(userMap.getId().getUserId());
    }*/
}