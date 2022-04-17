package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.controller.form.LoginForm;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String createLoginForm(@ModelAttribute("loginForm") LoginForm loginForm){
        return "users/loginForm";
    }

    @ResponseBody
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

        session.setAttribute("Login", loginUser);
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
}
