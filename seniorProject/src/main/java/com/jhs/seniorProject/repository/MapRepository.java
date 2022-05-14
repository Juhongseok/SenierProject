package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Map;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapRepository extends JpaRepository<Map, Long> {
    List<Map> findByModifiedBy(String modifiedBy);

    Optional<Map> findByModifiedByAndPassword(String modifiedBy, String password);
}
