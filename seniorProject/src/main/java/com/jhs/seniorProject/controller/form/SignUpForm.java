package com.jhs.seniorProject.controller.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SignUpForm {

    @NotEmpty(message = "필수 정보입니다")
    private String userId;

    @NotEmpty(message = "필수 정보입니다")
    private String password;

    @NotEmpty(message = "필수 정보입니다")
    private String passwordCheck;

    @NotEmpty(message = "필수 정보입니다")
    private String name;
}
