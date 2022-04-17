package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.controller.form.LoginForm;
import com.jhs.seniorProject.controller.form.SignUpForm;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotEmpty;

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
    public String signUp(@Validated @ModelAttribute SignUpForm signUpForm, BindingResult bindingResult){
        log.info("userController --> signUpForm: {}", signUpForm);

        checkPasswordEquals(bindingResult, signUpForm.getPassword(), signUpForm.getPasswordCheck());
        if (bindingResult.hasErrors()) {
            log.info("bindResult = {}", bindingResult);
            return "users/signUpForm";
        }

        User signUpUser = new User(signUpForm.getUserId(), signUpForm.getPassword(), signUpForm.getName());
        String userId;
        try {
            userId = userService.join(signUpUser);
        } catch (DuplicatedUserException e) {
            return "users/signUpForm";
        }

        log.info("signUpUserId: {}", userId);
        return "redirect:/";
    }

    @PostMapping("/withDrawl")
    public String withDrawl(String userId, @SessionAttribute(name = LOGIN_USER, required = false) User user) {
        if (user != null) {
            userService.withdrawal(userId);
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String createLoginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "users/loginForm";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm loginForm, BindingResult bindingResult, HttpSession session){
        log.info("userController --> user.id: {}, user.password: {}", loginForm.getUserId(), loginForm.getPassword());

        if (bindingResult.hasErrors()) {
            log.info("bindResult = {}", bindingResult);
            return "users/loginForm";
        }

        User loginUser = new User(loginForm.getUserId(), loginForm.getPassword(), null);
        if (userService.login(loginUser) == null) {
            log.info("has no login user");
            return "users/loginForm";
        }

        log.info("loginUser = {}", loginUser);
        session.setAttribute(LOGIN_USER, loginUser);
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

    private void checkPasswordEquals(BindingResult bindingResult, String password, String passwordCheck) {
        if (!password.equals(passwordCheck)) {
            bindingResult.rejectValue("NotCorrectPassword", "비밀번호가 일치하지 않습니다");
        }
    }
}
