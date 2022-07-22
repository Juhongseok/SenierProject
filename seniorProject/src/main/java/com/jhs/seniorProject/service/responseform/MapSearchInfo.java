package com.jhs.seniorProject.service.responseform;

import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MapSearchInfo {
    private BigSubject[] bigSubjects;
    private List<SmallSubjectInfo> smallSubjects = new ArrayList<>();
}
