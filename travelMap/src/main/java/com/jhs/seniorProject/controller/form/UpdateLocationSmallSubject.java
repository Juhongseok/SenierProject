package com.jhs.seniorProject.controller.form;

import com.jhs.seniorProject.domain.SmallSubject;
import lombok.Data;

@Data
public class UpdateLocationSmallSubject{
    Long id;
    String subjectName;

    public UpdateLocationSmallSubject(SmallSubject smallSubject) {
        this.id = smallSubject.getId();
        this.subjectName = smallSubject.getSubjectName();
    }
}