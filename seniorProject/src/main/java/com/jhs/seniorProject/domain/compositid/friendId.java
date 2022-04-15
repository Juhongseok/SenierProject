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
public class friendId implements Serializable {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FRIEND_ID")
    private String friendId;
}
