package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.baseentity.TimeInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "USERS")
@ToString(of = {"id", "name"})
public class User extends TimeInfo implements Persistable<String> {

    @Id
    @Column(name = "USER_ID")
    private String id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NAME")
    private String name;

    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    @Override
    public boolean isNew() {
        return getCreatedAt() == null;
    }

    //==비지니스 로직==//
    public void changePassword(String password) {
        this.password = password;
    }

    public void changeName(String name) {
        this.name = name;
    }

    //==조회 로직==//
    public boolean isSamePassword(String password){
        return this.password.equals(password);
    }
}
