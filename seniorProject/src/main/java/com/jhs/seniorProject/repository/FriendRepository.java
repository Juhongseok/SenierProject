package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.compositid.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    @Query("select f from Friend f join fetch f.friendId friend where f.userId.id = :userId")
    List<Friend> findByIdUserId(@Param("userId") String userId);
    Friend findByIdUserIdAndIdFriendId(String userId, String friendId);
}
