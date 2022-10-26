package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.SmallSubject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SmallSubjectRepository extends JpaRepository<SmallSubject, Long> {
    @Query("select sb from SmallSubject sb where sb.map.id = :mapId")
    List<SmallSubject> findByMapId(@Param("mapId") Long mapId);

    @Query("select sb from SmallSubject sb where sb.map.id = :mapId")
    Page<SmallSubject> findByMapId(@Param("mapId") Long mapId, Pageable pageable);
}
