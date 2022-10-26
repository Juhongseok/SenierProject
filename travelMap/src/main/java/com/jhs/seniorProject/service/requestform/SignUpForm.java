package com.jhs.seniorProject.service.requestform;

import com.jhs.seniorProject.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class SignUpForm {

    @NotEmpty(message = "필수 정보입니다")
    private String userId;

    @NotEmpty(message = "필수 정보입니다")
    private String password;

    @NotEmpty(message = "필수 정보입니다")
    private String passwordCheck;

    @NotEmpty(message = "필수 정보입니다")
    private String name;

    public boolean checkPassword() {
        return password.equals(passwordCheck);
    }

    public User toEntity(){
        return new User(userId, password, name);
    }

    public void setBlankAllData(){
        userId = "";
        password = "";
        passwordCheck = "";
        name = "";
    }
}
