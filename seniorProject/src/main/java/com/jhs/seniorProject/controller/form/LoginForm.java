package com.jhs.seniorProject.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginForm {

//    @NotEmpty(message = "아이디를 입력하세요")
    private String userId;
//    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;
}
