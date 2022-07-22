package com.jhs.seniorProject.controller;

import com.jhs.seniorProject.argumentresolver.Login;
import com.jhs.seniorProject.argumentresolver.LoginUser;
import com.jhs.seniorProject.service.SmallSubjectService;
import com.jhs.seniorProject.controller.form.AddSubjectRequest;
import com.jhs.seniorProject.service.requestform.AddSubjectDto;
import com.jhs.seniorProject.service.responseform.SmallSubjectInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subject")
@RequiredArgsConstructor
public class SmallSubjectController {

    private final SmallSubjectService smallSubjectService;

    @PatchMapping("/{subjectId}")
    public String updateSubject(@PathVariable("subjectId") Long subjectId, @RequestBody String changeName) {
        smallSubjectService.updateName(subjectId, changeName);
        return changeName;
    }

    @PostMapping("/add")
    public List<SmallSubjectInfo> addSubject(@RequestBody AddSubjectRequest addSubjectRequest, @Login LoginUser user) {
        Long mapId = smallSubjectService.addSubject(AddSubjectDto.from(addSubjectRequest, user.getId()));
        return smallSubjectService.getSubjects(mapId);
    }

}
