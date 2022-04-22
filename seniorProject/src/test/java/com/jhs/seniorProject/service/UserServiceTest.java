package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("회원가입 성공로직")
    void joinSuccess() throws DuplicatedUserException {
        //given
        User newUser = new User("userD", "userD!", "userD");

        //when
        String userId = userService.join(newUser);
        User findUser = userRepository.findById(newUser.getId()).get();

        //then
        assertThat(findUser.getId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("회원중복으로 인한 회원가입 실패")
    void joinFailByDuplicated(){
        //given
        User newUser = new User("userA", "userA!", "userA");

        //when
        //then
        assertThatThrownBy(() -> userService.join(newUser))
                .isInstanceOf(DuplicatedUserException.class);
    }

    @Test
    @DisplayName("회원탈퇴 성공로직")
    void withdrawalSuccess() throws NoSuchUserException {
        //given
        User userA = userRepository.findById("userA").get();

        //when
        userService.withdrawal(userA.getId());
        User findUser = userRepository.findById(userA.getId()).orElse(null);

        //then
        assertThat(findUser).isNull();
    }

    @Test
    @DisplayName("로그인하지않고 url로 바로 접속한 경우")
    void withdrawalFail(){
        //given
        User user = null;
        //when
        //then
        assertThatThrownBy(()->userService.withdrawal(user.getId()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("옳지않은 회원 아이디가 넘어와 탈퇴를 하는 경우")
    void withdrawalFailWithWrongId(){
        //given
        User user = new User("userADFASDFASDF", "userA", "userA");

        //when
        //then
        assertThatThrownBy(()->userService.withdrawal(user.getId()))
                .isInstanceOf(NoSuchUserException.class);
    }

    @Test
    @DisplayName("로그인 성공로직")
    void loginSuccess() throws NoSuchUserException {
        //given
        User user = new User("userA", "userA!", "userA");

        //when
        User loginUser = userService.login(user);
        User findUser = userRepository.findById(loginUser.getId()).get();

        //then
        assertThat(loginUser).isEqualTo(findUser);
        assertThat(loginUser.getId()).isEqualTo(findUser.getId());
        assertThat(loginUser.getPassword()).isEqualTo(findUser.getPassword());
        assertThat(loginUser.getName()).isEqualTo(findUser.getName());
    }

    @Test
    @DisplayName("비밀번호 오류로 인한 로그인 실패로직")
    void loginFailByWrongPassword(){
        //given
        User user = new User("userA", "userA", "userA");

        //when
        //then
        assertThatThrownBy(() -> userService.login(user))
                .isInstanceOf(NoSuchUserException.class);
    }

    @Test
    @DisplayName("아이디 오류로 인한 로그인 실패로직")
    void loginFailByWrongId(){
        //given
        User user = new User("userAbcd", "userA!", "userA");

        //when
        //then
        assertThatThrownBy(() -> userService.login(user))
                .isInstanceOf(NoSuchUserException.class);
    }
}