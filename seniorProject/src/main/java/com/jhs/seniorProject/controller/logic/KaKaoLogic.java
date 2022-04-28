package com.jhs.seniorProject.controller.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.jhs.seniorProject.controller.form.KaKaoInfo;
import com.jhs.seniorProject.controller.form.KaKaoToken;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;

import java.util.UUID;

import static com.jhs.seniorProject.controller.SessionConst.KAKAO_TOKEN;
import static com.jhs.seniorProject.controller.logic.kaKaoConst.*;

@Component
@RequiredArgsConstructor
public class KaKaoLogic {

    private String token;
    private RestTemplate restTemplate = new RestTemplate();

    private final UserService userService;

    public void getKaKaoToken(String code) {
        HttpHeaders headers = getHttpHeaders();
        MultiValueMap<String, String> params = getBodyData(code);
        HttpEntity<MultiValueMap<String, String>> kaKaoTokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = getResponseEntity(GET_TOKEN_URL, kaKaoTokenRequest);
        token = transferJsonDataToDTO(response, KaKaoToken.class).getAccessToken();
    }

    public User logIn(HttpSession session) {
        KaKaoInfo info = getKaKaoInfo();
        String password = UUID.randomUUID().toString().substring(0, 8);
        User kaKaoUser = null;
        try {
            kaKaoUser = new User(String.valueOf(info.getId()), password, info.getProperties().getNickname());
            userService.join(kaKaoUser);
        } catch (DuplicatedUserException e) {
        }
        session.setAttribute(KAKAO_TOKEN, token);
        return kaKaoUser;
    }

    public void logout(HttpSession session) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<MultiValueMap<String, String>> kaKaoTokenRequest = new HttpEntity<>(headers);
        ResponseEntity<String> response = getResponseEntity(LOGOUT_URL, kaKaoTokenRequest);
        if (response.getStatusCode() == HttpStatus.OK) {
            session.invalidate();
        }
    }

    //==비지니스 로직==//
    private KaKaoInfo getKaKaoInfo() {
        HttpHeaders headers = getHttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<MultiValueMap<String, String>> kaKaoProfileRequest = new HttpEntity<>(headers);
        ResponseEntity<String> response2 = getResponseEntity(GET_KAKAO_INFO_URL, kaKaoProfileRequest);
        return transferJsonDataToDTO(response2, KaKaoInfo.class);
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
        params.add("redirect_uri", REDIRECT_URL);
        params.add("code", code);
        return params;
    }

    private ResponseEntity<String> getResponseEntity(String url, HttpEntity<MultiValueMap<String, String>> httpEntity) {
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

    private <T> T transferJsonDataToDTO(ResponseEntity<String> response, Class<T> className) {
        T t = null;
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        try {
            t = mapper.readValue(response.getBody(), className);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }
}