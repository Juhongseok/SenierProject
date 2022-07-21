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
import com.jhs.seniorProject.service.responseform.LocationSearch;
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

import static com.jhs.seniorProject.domain.enumeration.BigSubject.TOGO;
import static com.jhs.seniorProject.domain.enumeration.BigSubject.WENT;

@Slf4j
@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final MapService mapService;
    private static List<BigSubject> togo;

    static {
        togo = List.of(TOGO, WENT);
    }

    //TODO 지도에 저장되어 있는 주제 검색 후 model 값에 넘기기
    @GetMapping("/{mapId}/view")
    public String Locations(@PathVariable("mapId") Long mapId, Model model) {
        return "location/locationviewmap";
    }

    @ResponseBody
    @GetMapping("/list")
    public ResponseEntity<List<LocationList>> getLocationList(@RequestParam Long mapId, @Login LoginUser user) {
        return new ResponseEntity<>(locationService.getLocations(mapId, user.getId()), HttpStatus.OK);
    }

    @GetMapping("/{mapId}/add")
    public String addLocationForm(@ModelAttribute(name = "saveLocationForm") SaveLocationForm saveLocationForm
            , @PathVariable Long mapId, @RequestParam Double lat, @RequestParam Double lng, @RequestParam String placeName
            , Model model) {
        log.info("lat = {}, lng = {}", lat, lng);

        saveLocationForm.setLongitude(lng);
        saveLocationForm.setLatitude(lat);
        saveLocationForm.setName(placeName);

        model.addAttribute("mapId", mapId)
                .addAttribute("bigSubjects", togo)
                .addAttribute("smallSubjects", locationService.getSmallSubjectList(mapId));
        return "location/addlocationform";
    }

    @PostMapping("/{mapId}/add")
    public String addLocation(@Login LoginUser user, @PathVariable Long mapId, @Validated @ModelAttribute SaveLocationForm locationForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "location/addlocationform";
        }

        log.info("saveLocationFor = {}", locationForm);
        locationService.saveLocation(locationForm, user.getId());


        redirectAttributes.addAttribute("mapId", mapId);
        return "redirect:/location/{mapId}/view";
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
    @GetMapping("/{location}/update")
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
    @PostMapping("/{location}/update")
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
    @GetMapping("/search")
    public List<LocationList> findLocationWithCond(@RequestBody LocationSearch locationSearch) {
        return locationService.findLocationWithCondV1(locationSearch);
//        return locationService.findLocationWithCondV2(locationSearch);
    }

    //== 기타 세팅 로직 ==//
    private void setModelAttribute(Long locationId, Model model) {
        Location findLocation = locationService.findLocation(locationId);

        model.addAttribute("locationId", locationId)
                .addAttribute("updateLocation", getUpdateLocationForm(findLocation))
                .addAttribute("smallSubjects", locationService.getSmallSubjectList(findLocation.getMap().getId()))
                .addAttribute("bigSubjects", togo);
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
