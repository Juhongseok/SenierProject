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
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Profile("local")
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
            userService.join(new SignUpForm("userA", "userA!", "userA","주홍석"));
            userService.join(new SignUpForm("userB", "userB!", "userB","userB"));

            friendService.addFriend(new LoginUser("userA", "주홍석"), "userB");
            friendService.addFriend(new LoginUser("userB", "userB"), "userA");

            mapService.createMap(new CreateMapDto("크리스마스 여행 지도", "userA"));
            mapService.createMap(new CreateMapDto("Sample Map", "userA"));

            UserInfoResponse userAInfo = userService.getUserInfo("userA", PageRequest.of(0,5));
            UserMapList maps = userAInfo.getMaps().getContent().get(0);
            Map map = mapRepository.findById(maps.getMapId()).get();
            List<SmallSubject> smallSubjects = smallSubjectRepository.findByMapId(map.getId());
            locationService.saveLocation(
                    new SaveLocationForm(37.53867557858969, 126.893055915673, "12월 24일 갈 숙소", "유니언호텔"
                            , BigSubject.TOGO, smallSubjects.get(2), map.getId())
                    ,"userA"
            );

            locationService.saveLocation(
                    new SaveLocationForm( 37.54214533095778, 126.89108775182187, "클라이밍 짐", "서울볼더스컴퍼니"
                            , BigSubject.TOGO, smallSubjects.get(3), map.getId())
                    ,"userA"
            );

            locationService.saveLocation(
                    new SaveLocationForm( 37.551507336391595, 126.917421754867 , "첫날 점심", "카와카츠 오토코"
                            , BigSubject.TOGO, smallSubjects.get(1), map.getId())
                    ,"userA"
            );
        } catch (DuplicatedUserException e) {

        } catch (NoSuchUserException e) {
            e.printStackTrace();
        } catch (DuplicateFriendException e) {
            e.printStackTrace();
        }
    }
}
