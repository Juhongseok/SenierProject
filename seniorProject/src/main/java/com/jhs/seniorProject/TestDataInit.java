package com.jhs.seniorProject;

import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.service.FriendService;
import com.jhs.seniorProject.service.MapService;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;
    private final FriendService friendService;
    private final MapService mapService;

    @PostConstruct
    public void init() {
        try {
            User userA = new User("userA", "userA!", "userA");
            User userB = new User("userB", "userB!", "userB");
            User userC = new User("userC", "userC!", "userC");
            userService.join(userA);
            userService.join(userB);
            userService.join(userC);

            friendService.addFriend(userA, "userB");
            mapService.createMap(userA.getName(), userA);

        } catch (DuplicatedUserException e) {

        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (DuplicateFriendException e) {
            e.printStackTrace();
        }
    }
}
