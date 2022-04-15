package com.jhs.seniorProject.domain.baseentity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@MappedSuperclass
public class TimeAndPersonInfo extends TimeInfo{

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;
}
