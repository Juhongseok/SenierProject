package com.jhs.seniorProject.jpa;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.service.MapService;
import com.jhs.seniorProject.service.requestform.CreateMapDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class OneToManyTest {

    @Autowired
    EntityManager em;

    @Autowired
    MapService mapService;

    /*@Test
    void test(){
        for (int i = 1; i <= 10; i++) {
            mapService.createMap(new CreateMapDto("map" + i, "userA"));
            mapService.createMap(new CreateMapDto("map" + i, "userB"));
        }

        List<UserMap> result = em.createQuery("select um from UserMap um where um.id.userId=:user", UserMap.class)
                .setParameter("user", "userA")
                .setFirstResult(0)
                .setMaxResults(5)
                .getResultList();

        //사용자가 추가한 지도 아이디 리스트
        List<Long> idList = result.stream()
                .map(um -> um.getId().getMapId())
                .collect(Collectors.toList());

        List<Map> mapResult = em.createQuery("select m from Map m join fetch m.userMaps um where um.id.mapId in :result", Map.class)
                .setParameter("result", idList)
                .getResultList();

        for (Map map : mapResult) {
            System.out.println(map.getName());
        }
    }*/
}
