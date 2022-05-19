package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
@Rollback(value = false)
class MapServiceTest {

    @Autowired
    private MapService mapService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MapRepository mapRepository;
    private Map savedMap;
    private User userA;
    private User userB;

    @BeforeEach
    void before() {
        savedMap = mapService.createMap("mapA", userRepository.findById("userA").get());
        userA = userRepository.findById("userA").get();
        userB = userRepository.findById("userB").get();
    }
    @Test
    @DisplayName("새 지도 생성")
    void createNewMap(){
        //given
        String mapName = "mapA";
        String mapName1 = "mapB";
        String mapName2 = "mapB";

        //when
        Map savedMap1 = mapService.createMap(mapName1, userA);
        Map savedMap2 = mapService.createMap(mapName2, userA);

        List<Map> maps = mapRepository.findByModifiedBy(userA.getName());
        //then
        assertThat(savedMap.getName()).isEqualTo(mapName);
        assertThat(savedMap.getCreatedBy()).isEqualTo(userA.getName());
        assertThat(maps.size()).isEqualTo(3);
        for (Map map : maps) {
            log.info("map : " +  map);
        }
    }

    @Test
    @DisplayName("지도 추가")
    void addMap() throws NoSuchMapException {
        Map map = mapService.addMap("userA", savedMap.getPassword(), userB);

    }
}