package com.jhs.seniorProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhs.seniorProject.controller.form.UpdateLocationForm;
import com.jhs.seniorProject.domain.baseentity.TimeAndPersonInfo;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "LOCATION_INFO")
public class Location extends TimeAndPersonInfo {

    @Id @Column(name = "LOCATION_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "MEMO")
    private String memo;

    @Column(name = "NAME")
    private String name;

    @Enumerated(STRING)
    @Column(name = "BIG_SUBJECT")
    private BigSubject bigSubject;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MAP_ID")
    private Map map;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SMALL_SUBJECT_ID")
    private SmallSubject smallSubject;

    public void chaneInfo(UpdateLocationForm locationForm) {
        this.memo = locationForm.getMemo();
        this.name = locationForm.getName();
        this.bigSubject = locationForm.getBigSubject();
        this.smallSubject = locationForm.getSmallSubject();
    }

    @Builder
    public Location(Double latitude, Double longitude, String memo, String name, BigSubject bigSubject, Map map, SmallSubject smallSubject, String userId) {
        super(userId);
        this.latitude = latitude;
        this.longitude = longitude;
        this.memo = memo;
        this.name = name;
        this.bigSubject = bigSubject;
        this.map = map;
        this.smallSubject = smallSubject;
    }
}
