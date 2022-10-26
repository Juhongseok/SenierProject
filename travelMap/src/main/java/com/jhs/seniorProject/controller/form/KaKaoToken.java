package com.jhs.seniorProject.controller.form;

import lombok.Data;

@Data
public class KaKaoToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String expiresIn;
    private String scope;
    private String refreshTokenExpiresIn;
}
