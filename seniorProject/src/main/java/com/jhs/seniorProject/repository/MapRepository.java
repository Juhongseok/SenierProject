package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MapRepository extends JpaRepository<Map, Long> {
    Optional<Map> findByCreatedByAndPassword(String createBy, String password);

    @Query("select m from Map m join fetch m.userMaps um where um.user.id = :userId")
    List<Map> findAll(@Param("userId") String userId);

    @Query("select m from Map m join fetch m.userMaps um where um.user.id =:userId and m.id =:mapId")
    Map findByIdWithUserMap(@Param("mapId") Long mapId, @Param("userId") String userId);
}
