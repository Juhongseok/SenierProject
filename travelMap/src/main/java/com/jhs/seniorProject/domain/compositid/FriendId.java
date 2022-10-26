package com.jhs.seniorProject.domain.compositid;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FriendId implements Serializable {

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "FRIEND_ID")
    private String friendId;
}
