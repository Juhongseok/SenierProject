package com.jhs.seniorProject.repository;

import com.jhs.seniorProject.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User savedUser;

    /*@BeforeEach
    void saveUser() {
        User user = new User("userA", "userA@", "userAName");
        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("유저 조회")
    void findUser() {
        //given
        String id = "userA";

        //when
        User findUser = userRepository.findById(id).get();

        //then
        assertThat(findUser).isEqualTo(savedUser);
        assertThat(findUser.getId()).isEqualTo(savedUser.getId());
        assertThat(findUser.getPassword()).isEqualTo(savedUser.getPassword());
        assertThat(findUser.getName()).isEqualTo(savedUser.getName());
    }

    @Test
    @DisplayName("유저 수정")
    void updateUser() {
        //given
        String id = "userA";
        User findUser = userRepository.findById(id).get();

        //when
        findUser.changePassword("userA!!");
        findUser.changeName("newUserName");

        //then

        User newFindUser = userRepository.findById(id).get();
        assertThat(newFindUser.getPassword()).isEqualTo("userA!!");
        assertThat(newFindUser.getName()).isEqualTo("newUserName");
    }

    @Test
    @DisplayName("유저 삭제")
    void deleteUser() {
        //given
        String id = "userA";
        User findUser = userRepository.findById(id).get();

        //when
        userRepository.delete(findUser);

        //then
        User newFindUser = userRepository.findById(id).orElse(null);
        assertThat(newFindUser).isNull();
    }*/

}
