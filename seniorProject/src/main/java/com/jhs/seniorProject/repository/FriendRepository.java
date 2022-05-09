package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.compositid.FriendId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {
    List<Friend> findByUserId(User userId);
    Friend findByUserIdAndFriendId(User userId, User friendId);
}
