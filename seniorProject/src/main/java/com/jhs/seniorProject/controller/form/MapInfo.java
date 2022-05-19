package com.jhs.seniorProject.controller.form;

import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.UserMap;
import lombok.Data;

import java.util.List;

@Data
public class MapInfo {
    Long id;
    String name;
    String createBy;
    List<UserMap> userMaps;

    public MapInfo(Map map) {
        this.id = map.getId();
        this.name = map.getName();
        this.createBy = map.getCreatedBy();
        userMaps = map.getUserMaps();
    }
}