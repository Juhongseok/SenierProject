package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.compositid.UserMapId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserMapRepository extends JpaRepository<UserMap, UserMapId> {

    @Query(value = "select um from UserMap um join fetch um.map where um.id.userId = :userId",
            countQuery = "select count(um) from UserMap um where um.id.userId = :userId")
    Page<UserMap> findByIdUserId(@Param("userId") String userId, Pageable pageable);

    Page<UserMap> findByIdMapId(Long mapId, Pageable pageable);
}
