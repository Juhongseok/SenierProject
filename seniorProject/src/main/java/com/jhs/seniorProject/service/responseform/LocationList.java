package com.jhs.seniorProject.service.responseform;

import lombok.Builder;
import lombok.Data;

@Data
public class LocationList {
    private Long locationId;
    private Double latitude;
    private Double longitude;
    private String name;

    @Builder
    public LocationList(Long locationId, Double latitude, Double longitude, String name) {
        this.locationId = locationId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }
}
