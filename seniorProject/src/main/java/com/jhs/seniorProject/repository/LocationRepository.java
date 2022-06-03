package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query("select l from Location l where l.map = :mapId")
    List<Location> findByMapId(@Param("mapId") Map mapId);
}
