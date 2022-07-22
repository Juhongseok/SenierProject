package com.jhs.seniorProject.service.responseform;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserMapList {
    private Long mapId;
    private String mapName;
    private String password;
}
