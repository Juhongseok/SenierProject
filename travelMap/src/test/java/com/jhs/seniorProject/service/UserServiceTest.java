package com.jhs.seniorProject.service;

import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.IncorrectPasswordException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.repository.UserRepository;
import com.jhs.seniorProject.service.requestform.LoginForm;
import com.jhs.seniorProject.service.requestform.SignUpForm;
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

    /*@Test
    @DisplayName("회원가입 성공로직")
    void joinSuccess() throws DuplicatedUserException {
        //given
        SignUpForm newUser = new SignUpForm("userD", "userD!", "userD","userD");

        //when
        userService.join(newUser);
        User findUser = userRepository.findById(newUser.getUserId()).get();

        //then
        assertThat(findUser.getId()).isEqualTo(newUser.getUserId());
    }

    @Test
    @DisplayName("회원중복으로 인한 회원가입 실패")
    void joinFailByDuplicated(){
        //given
        SignUpForm newUser = new SignUpForm("userA", "userA!", "userA!","userA");

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
    void loginSuccess() throws NoSuchUserException, IncorrectPasswordException {
        //given
        LoginForm newUser = new LoginForm("userA", "userA!");

        //when
        userService.login(newUser);
        User findUser = userRepository.findById(newUser.getUserId()).get();

        //then
        assertThat(newUser.getUserId()).isEqualTo(findUser.getId());
        assertThat(newUser.getPassword()).isEqualTo(findUser.getPassword());
    }

    @Test
    @DisplayName("비밀번호 오류로 인한 로그인 실패로직")
    void loginFailByWrongPassword(){
        //given
        LoginForm user = new LoginForm("userA", "userA");

        //when
        //then
        assertThatThrownBy(() -> userService.login(user))
                .isInstanceOf(IncorrectPasswordException.class);
    }

    @Test
    @DisplayName("아이디 오류로 인한 로그인 실패로직")
    void loginFailByWrongId(){
        //given
        LoginForm user = new LoginForm("userAbcd", "userA!");

        //when
        //then
        assertThatThrownBy(() -> userService.login(user))
                .isInstanceOf(NoSuchUserException.class);
    }*/
}