package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.controller.form.AddMapForm;
import com.jhs.seniorProject.service.requestform.FriendList;
import com.jhs.seniorProject.controller.form.MapForm;
import com.jhs.seniorProject.controller.form.MapInfo;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.service.FriendService;
import com.jhs.seniorProject.service.MapService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Controller
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;
    private final FriendService friendService;

    @GetMapping("/create")
    public String createMapForm(@ModelAttribute("mapForm") MapForm mapForm){
        return "map/createmapform";
    }

    /**
     * 새로운 지도 생성
     * @param mapForm
     * @param bindingResult
     * @param user
     * @return
     */
    @PostMapping("/create")
    public String createMap(@ModelAttribute("mapForm") @Validated MapForm mapForm, BindingResult bindingResult, @Login User user){
        if (!bindingResult.hasErrors()) {
            //TODO DTO 하나로 통합
            mapService.createMap(mapForm.getName(), user);
            return "redirect:/";
        }
        return "map/createmapform";
    }

    @GetMapping("/add")
    public String addMapForm(@ModelAttribute("addMapForm") AddMapForm addMapForm, @Login LoginUser user, Model model){
        log.info("add map");
        List<FriendList> friends = friendService.getFriends(user).stream()
                .map(FriendList::new)
                .collect(toList());

        model.addAttribute("friends", friends);
        return "map/addmapform";
    }

    /**
     * 기존 친구 지도 추가
     * @param addMapForm
     * @param bindingResult
     * @param user
     * @return
     */
    @PostMapping("/add")
    public String addMap(@ModelAttribute("addMapForm") @Validated AddMapForm addMapForm, BindingResult bindingResult, @Login User user){
        log.info("add map");
        if (bindingResult.hasErrors()) {
            return "map/addmapform";
        }
        try {
            log.info("addForm = {}", addMapForm);
            mapService.addMap(addMapForm.getId(), addMapForm.getPassword(), user);
        } catch (NoSuchMapException e) {
            bindingResult.reject("noUser","해당 사용자가 없습니다.");
        }
        return "redirect:/";
    }

    /**
     * 내 지도 리스트 확인
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String getMapList(@Login User user, Model model) {
        model.addAttribute("mapList", mapService.getMaps(user));
        return "map/mymaps";
    }

    /**
     * 지도 정보 보기
     * @param mapId
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/list/{mapId}")
    public String getMapInfoPage(@PathVariable Long mapId, @Login User user, Model model) {
        log.info("mapId = {}", mapId);
        try {
            model.addAttribute("info", new MapInfo(mapService.getMap(mapId, user.getId())));
        } catch (NoSuchMapException e) {
            e.printStackTrace();
        }
        return "map/mapinfo";
    }

    /**
     * 해당 사용자 지도 권한 부여
     * @param mapId
     * @param userId
     * @return
     */
    @PostMapping("/give_auth")
    public String giveAuthToUser(Long mapId, String userId) {
        try {
            mapService.accessMap(mapId, userId);
        } catch (NoSuchMapException e) {
            e.printStackTrace();
        }
        return null;
    }
}
