package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.controller.form.SaveLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationForm;
import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import com.jhs.seniorProject.service.LocationService;
import com.jhs.seniorProject.service.MapService;
import com.jhs.seniorProject.service.responseform.LocationList;
import com.jhs.seniorProject.controller.form.LocationSearch;
import com.jhs.seniorProject.service.responseform.MapSearchInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @ResponseBody
    @GetMapping("/locations")
    public ResponseEntity<List<LocationList>> getLocationList(@RequestParam Long mapId, @Login LoginUser user) {
        return new ResponseEntity<>(locationService.getLocations(mapId, user.getId()), HttpStatus.OK);
    }

    @GetMapping("/location/map/{mapId}")
    public String addLocationForm(@ModelAttribute(name = "saveLocationForm") SaveLocationForm saveLocationForm
            , @PathVariable Long mapId, @RequestParam Double lat, @RequestParam Double lng, @RequestParam String placeName
            , Model model) {
        log.info("lat = {}, lng = {}", lat, lng);

        saveLocationForm.setLongitude(lng);
        saveLocationForm.setLatitude(lat);
        saveLocationForm.setName(placeName);

        model.addAttribute("mapId", mapId)
                .addAttribute("bigSubjects", BigSubject.values())
                .addAttribute("smallSubjects", locationService.getSmallSubjectList(mapId));
        return "location/addlocationform";
    }

    @PostMapping("/location/map/{mapId}")
    public String addLocation(@Login LoginUser user, @PathVariable Long mapId, @Validated @ModelAttribute SaveLocationForm locationForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "location/addlocationform";
        }

        log.info("saveLocationFor = {}", locationForm);
        locationService.saveLocation(locationForm, user.getId());


        redirectAttributes.addAttribute("mapId", mapId);
        return "redirect:/map/{mapId}/view";
    }

    /**
     * if 사용자가 임의의 아이디를 고의적으로 넘길경우
     * findLocation --> throw IllegalArgumentException
     * ControllerAdvice 에러 처리
     *
     * @param locationId
     * @param model
     * @return
     */
    @GetMapping("/location/{location}/update")
    public String modifyLocationForm(@PathVariable(name = "location") Long locationId,
                                     @RequestParam Long mapId, Model model) {
        model.addAttribute("mapId", mapId);
        setModelAttribute(locationId, model);
        return "location/updatelocationform";
    }

    /**
     * location 변경사항 update
     *
     * @param locationId
     * @param updateLocationForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/location/{location}/update")
    public String modifyLocation(@PathVariable(name = "location") Long locationId, @Validated @ModelAttribute UpdateLocationForm updateLocationForm, BindingResult bindingResult,
                                 @RequestParam("mapId") Long mapId) {
        log.info("locationId = {}", locationId);
        if (bindingResult.hasErrors()) {
            return "location/updatelocationform";
        }

        locationService.updateLocation(locationId, updateLocationForm);
        return "redirect:/location/" + mapId + "/view";
    }

    /**
     * 저장된 위치 검색
     *
     * @param locationSearch
     * @return
     */
    @ResponseBody
    @PostMapping("/location/search")
    public List<LocationList> findLocationWithCond(@RequestBody LocationSearch locationSearch) {
        log.info("request body data : {}", locationSearch);
        return locationService.findLocationWithCondV1(locationSearch);
    }

    //== 기타 세팅 로직 ==//
    private void setModelAttribute(Long locationId, Model model) {
        Location findLocation = locationService.findLocation(locationId);

        model.addAttribute("locationId", locationId)
                .addAttribute("updateLocation", getUpdateLocationForm(findLocation))
                .addAttribute("smallSubjects", locationService.getSmallSubjectList(findLocation.getMap().getId()))
                .addAttribute("bigSubjects", BigSubject.values());
    }

    private UpdateLocationForm getUpdateLocationForm(Location findLocation) {
        return UpdateLocationForm.builder()
                .name(findLocation.getName())
                .bigSubject(findLocation.getBigSubject())
                .smallSubject(findLocation.getSmallSubject())
                .memo(findLocation.getMemo())
                .build();
    }

}
