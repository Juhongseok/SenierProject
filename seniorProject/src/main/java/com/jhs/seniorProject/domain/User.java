package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.baseentity.TimeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "USERS")
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

    /**
     * 사용자 정의 함수
     */
    public void changePassword(String password) {
        this.password = password;
    }

    public void changeName(String name) {
        this.name = name;
    }
}
