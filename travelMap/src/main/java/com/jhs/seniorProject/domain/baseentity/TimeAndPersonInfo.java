package com.jhs.seniorProject.domain.baseentity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@NoArgsConstructor
@MappedSuperclass
public class TimeAndPersonInfo extends TimeInfo{

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

    public TimeAndPersonInfo(String createdBy) {
        this.createdBy = createdBy;
        this.modifiedBy = createdBy;
    }
}
