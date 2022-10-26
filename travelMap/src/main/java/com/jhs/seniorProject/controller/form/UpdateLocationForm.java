package com.jhs.seniorProject.controller.form;

import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateLocationForm {
    private String memo;
    private String name;
    private BigSubject bigSubject;
    private SmallSubject smallSubject;
}
