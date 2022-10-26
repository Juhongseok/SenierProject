package com.jhs.seniorProject.service.requestform;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class LoginForm {

    @NotEmpty(message = "아이디를 입력하세요")
    private String userId;

    @NotEmpty(message = "비밀번호를 입력하세요")
    private String password;

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
