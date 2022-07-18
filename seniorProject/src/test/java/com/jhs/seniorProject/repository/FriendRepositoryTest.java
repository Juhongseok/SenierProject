package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.compositid.FriendId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private UserRepository userRepository;
    private User userA;
    private User userB;
    private FriendId id;

    @BeforeEach
    void before() {
        userA = userRepository.findById("userA").get();
        userB = userRepository.findById("userB").get();

        id = new FriendId(userA.getId(), userB.getId());
        Friend friend = new Friend(id, userA, userB);
        friendRepository.save(friend);

        User userA1 = userRepository.findById("userA").get();
        User userB1 = userRepository.findById("userC").get();

        FriendId id1 = new FriendId(userA1.getId(), userB1.getId());
        Friend friend1 = new Friend(id1, userA1, userB1);
        friendRepository.save(friend1);

        User userA2 = userRepository.findById("userB").get();
        User userB2 = userRepository.findById("userC").get();

        FriendId id2 = new FriendId(userA2.getId(), userB2.getId());
        Friend friend2 = new Friend(id2, userA2, userB2);
        friendRepository.save(friend2);
        
    }

//    @AfterEach
//    void after(){
//        friendRepository.deleteAll();
//    }
    @Test
    @DisplayName("친구 추가성공")
    void saveFriend() {
        Friend friend2 = friendRepository.findById(id).get();
        assertThat(userA).isEqualTo(friend2.getUserId());
        assertThat(userB).isEqualTo(friend2.getFriendId());
    }

    @Test
    @DisplayName("친구 목록 보기")
    void findFriends(){
        //given
        User userA = userRepository.findById("userA").get();
        //when
        List<Friend> findFriends = friendRepository.findByIdUserId(userA.getId());

        //then
        for (Friend findFriend : findFriends) {
            System.out.println(findFriend.getFriendId().getName());
        }
    }

    @Test
    @DisplayName("내 아이디랑 친구아이디로 검색")
    void findFriendWithUserIdAndFriendId(){
        //given
        User userA = userRepository.findById("userA").get();
        User userB = userRepository.findById("userD").orElse(null);
        Friend find = friendRepository.findByIdUserIdAndIdFriendId(userA.getId(), userB.getId());
        //when
        System.out.println(find);
        //then
    }
}