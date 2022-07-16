package com.jhs.seniorProject.service.responseform;

import com.jhs.seniorProject.domain.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor(access = PRIVATE)
public class MapList {
    private String mapName;

    public static MapList from(Map map) {
        return new MapList(map.getName());
    }
}
