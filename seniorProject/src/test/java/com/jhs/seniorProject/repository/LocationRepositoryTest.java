package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.service.requestform.LocationSearchDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jhs.seniorProject.domain.enumeration.BigSubject.TOGO;

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

    @Autowired
    EntityManager em;

    /*@Test
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

    @Test
    void smallSubject(){
        List<Location> result = em.createQuery(
                "select l from Location l join l.smallSubject " +
                        "where l.smallSubject.subjectName =:subjectName"
                        ,Location.class)
                .setParameter("subjectName", "카페")
                .getResultList();

//        for (Location l : result) {
//            System.out.println(l.getSmallSubject());
//        }

    }

    @Test
    void customRepository(){
        List<Location> locationCond = locationRepository.findLocationCond(LocationSearchDto.builder()
                .mapId(1L)
                .name("location1")
                .bigSubject(TOGO)
                .smallSubject(2l)
                .build());
        for (Location location : locationCond) {
            System.out.println(location.getSmallSubject().getSubjectName());
        }
    }*/
}