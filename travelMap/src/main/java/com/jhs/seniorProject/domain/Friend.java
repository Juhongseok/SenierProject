package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.compositid.FriendId;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor
@ToString(of = {"userId", "friendId"})
@Table(name = "FRIEND")
public class Friend implements Persistable<FriendId> {

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

    @Transient
    private transient boolean persisted;

    public Friend(FriendId id, User userId, User friendId) {
        this.id = id;
        this.userId = userId;
        this.friendId = friendId;
    }

    @Override
    public boolean isNew() {
        return !persisted;
    }

    @PostPersist
    @PostLoad
    public void setPersisted(){
        persisted = true;
    }
}
