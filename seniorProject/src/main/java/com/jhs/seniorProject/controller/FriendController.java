package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.service.requestform.FriendForm;
import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.service.FriendService;
import com.jhs.seniorProject.service.responseform.FriendList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;


@Slf4j
@Controller
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/friends")
    public String getFriends(@Login LoginUser loginUser, @ModelAttribute("friend") FriendForm friendForm, Model model) {
        List<String> result = friendService.getFriends(loginUser).stream()
                .map(FriendController::getFriendName)
                .collect(toList());
        model.addAttribute("friends", result);
        return "friend/friendList";
    }

    @ResponseBody
    @PostMapping("/friend")
    public void addFriend(@Valid @RequestBody FriendForm friendForm, BindingResult bindingResult, @Login LoginUser loginUser) throws BindException, NoSuchUserException, DuplicateFriendException {
        log.info("add friend {}", friendForm);
        if (bindingResult.hasErrors()) {
            log.info("bindingResult has error, ", bindingResult);
            throw new BindException(bindingResult);
        }
        friendService.addFriend(loginUser, friendForm.getId());
    }

    private static String getFriendName(FriendList friend) {
        return friend.getName();
    }
}
