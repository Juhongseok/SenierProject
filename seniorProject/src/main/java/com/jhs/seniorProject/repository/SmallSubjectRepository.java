package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SmallSubjectRepository extends JpaRepository<SmallSubject, Long> {
    List<SmallSubject> findByMap(Map map);
}
