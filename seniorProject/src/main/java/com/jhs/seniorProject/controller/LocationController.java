package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.controller.form.SaveLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationSmallSubject;
import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.User;
import com.jhs.seniorProject.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.jhs.seniorProject.domain.enumeration.BigSubject.TOGO;
import static com.jhs.seniorProject.domain.enumeration.BigSubject.WENT;

@Slf4j
@Controller
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    /**
     * kakao map 연동 시 사용할 위치 리스트 반환
     * 추후 api 통신을 할 경우 변경
     *
     * @param mapId
     * @param model
     * @return
     */
    @GetMapping("/view")
    public String Locations(/*@RequestParam("mapId") Long mapId, Model model*/) {
//        model.addAttribute("locations", locationService.locations(mapId));
        return "location/locationviewmap";
    }

    @ResponseBody
    @PostMapping("/add")
    public void addLocation(@Login User user, @Validated @RequestBody SaveLocationForm locationForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //에러던짐
            throw new RuntimeException();
        }

        locationService.saveLocation(locationForm, user.getId());
    }

    /**
     * if 사용자가 임의의 아이디를 고의적으로 넘길경우
     * findLocation --> throw IllegalArgumentException
     * ControllerAdvice 에러 처리
     * @param locationId
     * @param model
     * @return
     */
    @GetMapping("/{location}/update")
    public String modifyLocationForm(@PathVariable(name = "location") Long locationId, Model model) {
        setModelAttribute(locationId, model);
        return "location/updatelocationform";
    }

    /**
     * location 변경사항 update
     * @param locationId
     * @param updateLocationForm
     * @param bindingResult
     * @return
     */
    @PostMapping("/{location}/update")
    public String modifyLocation(@PathVariable(name = "location") Long locationId, @Validated @ModelAttribute UpdateLocationForm updateLocationForm, BindingResult bindingResult) {
        log.info("locationId = {}", locationId);
        if (bindingResult.hasErrors()) {
            return "location/updatelocationform";
        }

        locationService.updateLocation(locationId, updateLocationForm);
        return "redirect:/"; // 지도화면 만든 후 경로 변경
    }

    private void setModelAttribute(Long locationId, Model model) {
        Location findLocation = locationService.findLocation(locationId);

        model.addAttribute("locationId", locationId)
            .addAttribute("updateLocation", getUpdateLocationForm(findLocation))
            .addAttribute("smallSubjects", locationService.getSmallSubjectList(findLocation.getMap()))
            .addAttribute("bigSubjects", List.of(TOGO, WENT));
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
