package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.baseentity.TimeAndPersonInfo;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "SMALL_SUBJECT_ID")
    private SmallSubject smallSubject;
}
