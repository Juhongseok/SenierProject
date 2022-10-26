package com.jhs.seniorProject.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jhs.seniorProject.domain.baseentity.TimeAndPersonInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MAP")
@ToString(of = {"id", "password", "name"})
public class Map extends TimeAndPersonInfo {

    @Id @Column(name = "MAP_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "map", cascade = ALL)
    private List<UserMap> userMaps = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "map", cascade = ALL)
    private List<SmallSubject> smallSubjects = new ArrayList<>();

    public Map(String name, String createdBy) {
        super(createdBy);
        this.password = UUID.randomUUID().toString().substring(0, 8);
        this.name = name;
    }

    public Map(String name, String createdBy, String password) {
        super(createdBy);
        this.password = password;
        this.name = name;
    }
}
