package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.compositid.UserMapId;
import com.jhs.seniorProject.domain.enumeration.Visibility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static com.jhs.seniorProject.domain.enumeration.Visibility.CLOSE;
import static com.jhs.seniorProject.domain.enumeration.Visibility.OPEN;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "visibility"})
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

    @Enumerated(STRING)
    @Column(name = "VISIBILITY")
    private Visibility visibility;

    public UserMap(UserMapId id, User user, Map map) {
        this.id = id;
        this.user = user;
        this.map = map;
        this.visibility = OPEN;
    }

    public void canNotVisibility() {
        visibility = CLOSE;
    }

    public void canVisibility() {
        visibility = OPEN;
    }
}
