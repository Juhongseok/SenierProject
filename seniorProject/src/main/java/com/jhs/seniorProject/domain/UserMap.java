package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.compositid.UserMapId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_MAP")
public class UserMap {

    @EmbeddedId
    private UserMapId id;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @MapsId("mapId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MAP_ID")
    private Map map;
}
