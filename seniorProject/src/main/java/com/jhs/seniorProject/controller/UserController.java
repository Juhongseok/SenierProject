package com.jhs.seniorProject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jhs.seniorProject.controller.form.KakaoInfo;
import com.jhs.seniorProject.controller.form.KakaoToken;
import com.jhs.seniorProject.controller.form.LoginForm;
import com.jhs.seniorProject.controller.form.SignUpForm;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.IncorrectPasswordException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

import java.util.UUID;

import static com.jhs.seniorProject.controller.SessionConst.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String createSignUpForm(@ModelAttribute("signUpForm") SignUpForm signUpForm) {
        return "users/signUpForm";
    }

    @PostMapping("/signup")
    public String signUp(@Validated @ModelAttribute SignUpForm signUpForm, BindingResult bindingResult) {
        log.info("userController --> signUpForm: {}", signUpForm);

        if (bindingResult.hasErrors()) {
            log.info("bindResult = {}", bindingResult);
            return "users/signUpForm";
        }

        if (!signUpForm.getPassword().equals(signUpForm.getPasswordCheck())) {
            bindingResult.reject("NotCorrectPassword", "비밀번호가 일치하지 않습니다");
            return "users/signUpForm";
        }

        User signUpUser = new User(signUpForm.getUserId(), signUpForm.getPassword(), signUpForm.getName());
        String userId;
        try {
            userId = userService.join(signUpUser);
        } catch (DuplicatedUserException e) {
            log.error("userController.signUp error ", e);
            bindingResult.reject("duplicateUser", "중복된 회원입니다.");
            return "users/signUpForm";
        }

        log.info("signUpUserId: {}", userId);
        return "redirect:/";
    }

    @PostMapping("/withdrawal")
    public String withdrawal(@SessionAttribute(name = LOGIN_USER, required = false) User user, HttpSession session) {
        if (isLoginStatus(user)) {
            try {
                userService.withdrawal(user.getId());
                session.invalidate();
            } catch (NoSuchUserException e) {
                log.error("UserController.withdrawal error", e);
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String createLoginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession session,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        log.info("userController --> user.id: {}, user.password: {}", loginForm.getUserId(), loginForm.getPassword());
        if (bindingResult.hasErrors()) {
            log.info("bindResult = {}", bindingResult);
            return "users/loginForm";
        }

        User loginUser = new User(loginForm.getUserId(), loginForm.getPassword(), null);
        User findLoginUser;
        try {
            findLoginUser = userService.login(loginUser);
        } catch (NoSuchUserException e) {
            log.error("UserController.login error", e);
            bindingResult.reject("NoSuchUser", "아이디가 일치하지 않습니다.");
            return "users/loginForm";
        } catch (IncorrectPasswordException e) {
            log.error("UserController.login error", e);
            bindingResult.reject("NotCorrectPassword", "비밀번호가 일치하지 않습니다");
            return "users/loginForm";
        }

        log.info("loginUser = {}", findLoginUser);
        session.setAttribute(LOGIN_USER, findLoginUser);
        return "redirect:" + redirectURL;
    }

    /**
     * user.id = kakao.id
     * user.name = kakao.properties.nickname
     * user.password = UUID.randomUUID
     */
    @GetMapping("/kakao_login")
    public String kakaoLogin(String code, HttpSession session) {
        log.info("kakao login execute");
        RestTemplate restTemplate1 = new RestTemplate();

        //로그인 인가 토큰 받기
        HttpHeaders headers1 = getHttpHeaders();
        MultiValueMap<String, String> params1 = getBodyData(code);
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params1, headers1);
        ResponseEntity<String> response = restTemplate1.exchange(
                "https://kauth.kakao.com/oauth/token"
                , HttpMethod.POST
                , kakaoTokenRequest
                , String.class
        );

        ObjectMapper mapper = new ObjectMapper();
        KakaoToken token = transferJsonDataToDTO(mapper, response, KakaoToken.class);

        log.info("kakao AccessToken : {}", token);
        log.info("finish get kakao AccessToken");

        //토근 사용해서 사용자 정보 받아오기
        RestTemplate restTemplate2 = new RestTemplate();
        HttpHeaders headers2 = getHttpHeaders();
        headers2.add("Authorization", "Bearer " + token.getAccess_token());
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);
        ResponseEntity<String> response2 = restTemplate2.exchange(
                "https://kapi.kakao.com/v2/user/me"
                , HttpMethod.POST
                , kakaoProfileRequest
                , String.class
        );

        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        KakaoInfo info = transferJsonDataToDTO(mapper, response2, KakaoInfo.class);

        String password = UUID.randomUUID().toString().substring(0, 8);
        log.info("kakao id : {}, kakao nickName : {}, password : {}"
                , info.getId(), info.getProperties().getNickname(), password);

        //사용자 정보 회원가입 및 로그인처리
        User kakaoUser = null;
        try {
            kakaoUser = new User(String.valueOf(info.getId()), password, info.getProperties().getNickname());
            userService.join(kakaoUser);
        } catch (DuplicatedUserException e) {
            return "users/loginForm";
        }

        log.info("login Success");
        log.info("loginUser = {}", kakaoUser);
        session.setAttribute(LOGIN_USER, kakaoUser);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        log.info("logout execute");
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
    private boolean isLoginStatus(User user) {
        return user != null;
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    private MultiValueMap<String, String> getBodyData(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "102364fc8aaacc13b119bdf84ae58bcd");
        params.add("redirect_uri", "http://localhost:8080/users/kakao_login");
        params.add("code", code);
        return params;
    }

    private <T> T transferJsonDataToDTO(ObjectMapper mapper, ResponseEntity<String>response, Class<T> className){
        T t = null;
        try {
            t = mapper.readValue(response.getBody(), className);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

}
