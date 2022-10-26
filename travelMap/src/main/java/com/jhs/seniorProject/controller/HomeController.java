package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.jhs.seniorProject.controller.SessionConst.LOGIN_USER;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Login LoginUser loginUser, Model model){
        if(loginUser == null)
            return "index";

        model.addAttribute(LOGIN_USER, loginUser);
        return "loginIndex";
    }
}
