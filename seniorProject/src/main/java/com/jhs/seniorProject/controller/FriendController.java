package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.controller.form.FriendForm;
import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.service.FriendService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Slf4j
@Controller
@RequestMapping("/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/list")
    public String getFriends(@Login User user, @ModelAttribute("user") FriendForm friendForm, Model model) {
        List<String> result = friendService.getFriends(user).stream()
                .map(FriendController::apply)
                .collect(toList());
        model.addAttribute("friends", result);
        return "friend/friendList";
    }

    @ResponseBody
    @PostMapping("/api/add")
    public void addFriend1(@Valid @RequestBody FriendForm friendForm, BindingResult bindingResult, @Login User user) throws BindException {
        log.info("add friend {}", friendForm);
        if (bindingResult.hasErrors()) {
            log.info("bindingResult has error, ", bindingResult);
            throw new BindException(bindingResult);
        }
        try {
            friendService.addFriend(user, friendForm.getId());
        } catch (DuplicateFriendException e) {
            e.printStackTrace();
        } catch (NoSuchUserException e) {
            e.printStackTrace();
        }
    }

    private static String apply(Friend friend) {
        return friend.getFriendId().getName();
    }
}
