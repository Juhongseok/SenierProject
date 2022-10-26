package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.repository.UserRepository;
import com.jhs.seniorProject.service.requestform.AddMapDto;
import com.jhs.seniorProject.service.requestform.CreateMapDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

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
    private User userA;
    private User userB;

   /* @BeforeEach
    void before() {
        mapService.createMap(new CreateMapDto("mapA", userRepository.findById("userA").get().getId()));
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
        mapService.createMap(new CreateMapDto(mapName1, userA.getId()));
        mapService.createMap(new CreateMapDto(mapName2, userA.getId()));

        //then
//        assertThat(savedMap.getName()).isEqualTo(mapName);
//        assertThat(savedMap.getCreatedBy()).isEqualTo(userA.getName());
    }

    @Test
    @DisplayName("지도 추가")
    void addMap() throws NoSuchMapException {
//        mapService.addMap(new AddMapDto("userA", savedMap.getPassword(), userB.getId()));

    }*/
}