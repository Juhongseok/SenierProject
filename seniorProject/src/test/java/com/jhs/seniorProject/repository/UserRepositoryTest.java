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
    private User user;

    @BeforeEach
    void saveUser() {
        user = new User("userA", "userA@", "userAName");
        User savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("유저 조회")
    void findUser() {
        //given
        String id = user.getId();

        //when
        User findUser = userRepository.findById(id).get();

        //then
        assertThat(findUser).isEqualTo(user);
        assertThat(findUser.getId()).isEqualTo(user.getId());
        assertThat(findUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(findUser.getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("유저 수정")
    void updateUser() {
        //given
        String id = user.getId();
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
        String id = user.getId();
        User findUser = userRepository.findById(id).get();

        //when
        userRepository.delete(findUser);

        //then
        User newFindUser = userRepository.findById(id).orElse(null);
        assertThat(newFindUser).isNull();
    }

}
