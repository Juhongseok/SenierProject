package com.jhs.seniorProject;

import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.controller.form.SaveLocationForm;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import com.jhs.seniorProject.domain.exception.DuplicateFriendException;
import com.jhs.seniorProject.domain.exception.DuplicatedUserException;
import com.jhs.seniorProject.domain.exception.NoSuchUserException;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.SmallSubjectRepository;
import com.jhs.seniorProject.service.FriendService;
import com.jhs.seniorProject.service.LocationService;
import com.jhs.seniorProject.service.MapService;
import com.jhs.seniorProject.service.UserService;
import com.jhs.seniorProject.service.requestform.CreateMapDto;
import com.jhs.seniorProject.service.requestform.SignUpForm;
import com.jhs.seniorProject.service.responseform.UserInfoResponse;
import com.jhs.seniorProject.service.responseform.UserMapList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final MapRepository mapRepository;
    private final SmallSubjectRepository smallSubjectRepository;

    @PostConstruct
    public void init() {
        try {
            SignUpForm userA_ = new SignUpForm("userA", "userA!", "userA","userA");
            SignUpForm userB_ = new SignUpForm("userB", "userB!", "userB","userB");
            SignUpForm userC_ = new SignUpForm("userC", "userC!", "userC","userC");
            userService.join(userA_);
            userService.join(userB_);
            userService.join(userC_);

            User userA = new User("userA", "userA!", "userA");
            User userB = new User("userB", "userB!", "userB");
            friendService.addFriend(new LoginUser(userA.getId(), userA.getName()), "userB");
            friendService.addFriend(new LoginUser(userA.getId(), userA.getName()), "userC");
            friendService.addFriend(new LoginUser(userB.getId(), userB.getName()), "userA");
            mapService.createMap(new CreateMapDto("UserA's Map1", userA.getId()));
            mapService.createMap(new CreateMapDto("UserA's Map2", userA.getId()));

            UserInfoResponse userAInfo = userService.getUserInfo("userA", PageRequest.of(0,10));
            UserMapList maps = (UserMapList) userAInfo.getMaps().getContent().get(0);
            UserMapList maps2 = (UserMapList) userAInfo.getMaps().getContent().get(1);
            Map map = mapRepository.findById(maps.getMapId()).get();
            Map map2 = mapRepository.findById(maps2.getMapId()).get();
            List<SmallSubject> smallSubjects = smallSubjectRepository.findByMapId(map.getId());
            locationService.saveLocation(
                    new SaveLocationForm(37.566826, 126.9786567, "save location1", "location1"
                            , BigSubject.TOGO, smallSubjects.get(0), map.getId())
                    ,userA.getId()
            );

            locationService.saveLocation(
                    new SaveLocationForm( 37.65358170124324, 127.04783326913316, "save location1", "location1"
                            , BigSubject.TOGO, smallSubjects.get(1), map.getId())
                    ,userA.getId()
            );

            locationService.saveLocation(
                    new SaveLocationForm( 37.615355069395335, 127.01335100479028 , "save location1", "location1"
                            , BigSubject.TOGO, smallSubjects.get(0), map.getId())
                    ,userA.getId()
            );

            locationService.saveLocation(
                    new SaveLocationForm( 37.615355069395335, 127.01335100479028 , "save location1", "location1"
                            , BigSubject.TOGO, smallSubjects.get(1), map2.getId())
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
