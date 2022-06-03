package com.jhs.seniorProject.controller.form;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaveLocationForm {
    private Double latitude;
    private Double longitude;
    private String memo;
    private String name;
    private BigSubject bigSubject;
    private SmallSubject smallSubject;
    private Map map;
}
