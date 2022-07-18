package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
@Rollback(value = false)
class LocationRepositoryTest {

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    SmallSubjectRepository smallSubjectRepository;

    @Autowired
    MapRepository mapRepository;

    @Test
    void findLocationsByMapId(){
        Map map = mapRepository.findById(1L).get();
        List<Location> list = locationRepository.findLocationsByMapId(map);
        for (Location location : list) {
            System.out.println(location);
        }
    }

    @Test
    void findByMapId(){
        List<SmallSubject> byMapId = smallSubjectRepository.findByMapId(1L);
        for (SmallSubject smallSubject : byMapId) {
            System.out.println("smallSubject = " + smallSubject.getSubjectName());
        }
    }
}