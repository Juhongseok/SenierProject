package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
        getFriends(user, model);
        return "friend/friendList";
    }

    @PostMapping("/add")
    public String addFriend(@Login User user, @RequestBody FriendForm friendForm, Model model){
        try {
            friendService.addFriend(user, friendForm.getId());
            log.info("success addFriend");
        } catch (DuplicateFriendException e) {
            log.error("error : ", e);
        } catch (NoSuchUserException e) {
            log.error("error : ", e);
        }

        getFriends(user, model);
        return "friend/friendList :: #friendTable";
    }

    private void getFriends(User user, Model model) {
        List<String> result = friendService.getFriends(user).stream()
                .map(FriendController::apply)
                .collect(toList());
        model.addAttribute("friends", result);
    }

    private static String apply(Friend friend) {
        return friend.getFriendId().getName();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class FriendForm{
        @NotNull
        String id;
    }
}
