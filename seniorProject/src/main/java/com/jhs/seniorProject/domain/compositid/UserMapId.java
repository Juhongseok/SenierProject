package com.jhs.seniorProject.domain.compositid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class UserMapId implements Serializable {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "MAP_ID")
    private Long mapId;
}
