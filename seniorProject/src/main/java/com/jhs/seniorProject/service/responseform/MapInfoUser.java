package com.jhs.seniorProject.service.responseform;

import lombok.Builder;
import lombok.Data;

@Data
public class MapInfoUser implements MapInfoResponse{
    private Long mapId;
    private String mapName;
    private String password;

    @Builder
    public MapInfoUser(Long mapId, String mapName, String password) {
        this.mapId = mapId;
        this.mapName = mapName;
        this.password = password;
    }
}
