package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.baseentity.TimeAndPersonInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "MAP")
public class Map extends TimeAndPersonInfo {

    @Id @Column(name = "MAP_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;
}
