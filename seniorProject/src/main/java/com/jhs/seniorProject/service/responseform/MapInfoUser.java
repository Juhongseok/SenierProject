package com.jhs.seniorProject.service.responseform;

import lombok.Builder;
import lombok.Data;

@Data
public class MapInfoUser implements MapInfoResponse{
    private String mapName;
    private String password;

    @Builder
    public MapInfoUser(String mapName, String password) {
        this.mapName = mapName;
        this.password = password;
    }
}
