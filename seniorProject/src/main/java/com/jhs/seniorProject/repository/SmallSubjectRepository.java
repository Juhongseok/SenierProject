package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;

public interface SmallSubjectRepository extends JpaRepository<SmallSubject, Long> {
    @Query("select sb from SmallSubject sb where sb.map.id = :mapId")
    List<SmallSubject> findByMapId(@Param("mapId") Long mapId);
}
