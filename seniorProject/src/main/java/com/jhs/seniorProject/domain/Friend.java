package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.compositid.FriendId;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"userId", "friendId"})
@Table(name = "FRIEND")
public class Friend {

    @EmbeddedId
    private FriendId id;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User userId;

    @MapsId("friendId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "FRIEND_ID")
    private User friendId;
}
