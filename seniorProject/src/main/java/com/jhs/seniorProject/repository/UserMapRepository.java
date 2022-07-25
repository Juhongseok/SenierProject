package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.compositid.UserMapId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMapRepository extends JpaRepository<UserMap, UserMapId> {

    Page<UserMap> findByIdUserId(String userId, Pageable pageable);
}
