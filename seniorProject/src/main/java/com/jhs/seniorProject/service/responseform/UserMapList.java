package com.jhs.seniorProject.service.responseform;

import com.jhs.seniorProject.domain.UserMap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMapList {
    private Long mapId;
    private String mapName;
    private String password;

    public UserMapList(UserMap userMap) {
        this.mapId = userMap.getMap().getId();
        this.mapName = userMap.getMap().getName();
        this.password = userMap.getMap().getPassword();
    }
}
