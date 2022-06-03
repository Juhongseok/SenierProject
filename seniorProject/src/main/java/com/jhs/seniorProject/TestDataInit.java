package com.jhs.seniorProject;

import com.jhs.seniorProject.controller.form.SaveLocationForm;
import com.jhs.seniorProject.domain.Friend;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.service.FriendService;
import com.jhs.seniorProject.service.LocationService;
import com.jhs.seniorProject.service.MapService;
import com.jhs.seniorProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    private final UserService userService;
    private final FriendService friendService;
    private final MapService mapService;
    private final LocationService locationService;

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
            Map map = mapService.createMap(userA.getName(), userA);
            List<SmallSubject> smallSubjects = map.getSmallSubjects();
            locationService.saveLocation(
                    new SaveLocationForm(1.2, 1.2, "save location1", "location1"
                            , BigSubject.TOGO, smallSubjects.get(0), map)
                    ,userA.getId()
            );
        } catch (DuplicatedUserException e) {

        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (DuplicateFriendException e) {
            e.printStackTrace();
        }
    }
}
