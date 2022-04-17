package com.jhs.seniorProject;

import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;

    @PostConstruct
    public void init() {
        try {
            userService.join(new User("userA", "userA!", "userA"));
            userService.join(new User("userB", "userB!", "userB"));
            userService.join(new User("userC", "userC!", "userC"));
        } catch (DuplicatedUserException e) {

        }
    }
}
