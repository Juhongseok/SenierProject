package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import static com.jhs.seniorProject.controller.SessionConst.LOGIN_USER;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name = LOGIN_USER, required = false) User loginUser, Model model){
        if(loginUser == null)
            return "index";

        model.addAttribute("user", loginUser);
        return "loginIndex";
    }
}
