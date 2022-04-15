package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.compositid.friendId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "FRIEND")
public class Friend {

    @EmbeddedId
    private friendId id;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @MapsId("friendId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FRIEND_ID")
    private User friend;
}
