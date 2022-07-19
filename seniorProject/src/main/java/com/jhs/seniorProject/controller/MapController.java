package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.controller.form.AddMapForm;
import com.jhs.seniorProject.controller.form.MapForm;
import com.jhs.seniorProject.domain.exception.NoSuchMapException;
import com.jhs.seniorProject.service.FriendService;
import com.jhs.seniorProject.service.MapService;
import com.jhs.seniorProject.service.requestform.AddMapDto;
import com.jhs.seniorProject.service.requestform.CreateMapDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public String createMap(@ModelAttribute("mapForm") @Validated MapForm mapForm, BindingResult bindingResult, @Login LoginUser user){
        if (bindingResult.hasErrors()) {
            return "map/createmapform";
        }
        String createMapName = mapService.createMap(new CreateMapDto(mapForm.getName(), user.getId()));
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addMapForm(@ModelAttribute("addMapForm") AddMapForm addMapForm, @Login LoginUser loginUser, Model model){
        model.addAttribute("friends", friendService.getFriends(loginUser));
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
    public String addMap(@ModelAttribute("addMapForm") @Validated AddMapForm addMapForm, BindingResult bindingResult, @Login LoginUser user){
        log.info("add map");
        if (bindingResult.hasErrors()) {
            return "map/addmapform";
        }
        try {
            log.info("addForm = {}", addMapForm);
            AddMapDto addMapDto = AddMapDto.builder()
                    .createUserId(addMapForm.getId())
                    .password(addMapForm.getPassword())
                    .addUserId(user.getId())
                    .build();

            String addMapName = mapService.addMap(addMapDto);
        } catch (NoSuchMapException e) {
            bindingResult.reject("noUser","해당 사용자가 없습니다.");
        }
        return "redirect:/";
    }

    /**
     * 지도 정보 보기
     * @param mapId
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/list/{mapId}")
    public String getMapInfoPage(@PathVariable Long mapId, @Login LoginUser user, Model model) {
        log.info("mapId = {}", mapId);
        try {
            model.addAttribute("info", mapService.getMapInfo(mapId, user.getId()));
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
    @ResponseBody
    @PostMapping("/give_auth/{mapId}/{userId}")
    public String giveAuthToUser(@PathVariable Long mapId, @PathVariable String userId) {
        try {
            mapService.accessMap(mapId, userId);
        } catch (NoSuchMapException e) {
            e.printStackTrace();
        }
        return "ok";
    }
}
