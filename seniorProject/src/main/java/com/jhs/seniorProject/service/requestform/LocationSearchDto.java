package com.jhs.seniorProject.service.requestform;

import com.jhs.seniorProject.controller.form.LocationSearch;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.Builder;
import lombok.Data;

@Data
public class LocationSearchDto {
    private Long mapId;
    private String name;
    private BigSubject bigSubject;
    private String smallSubject;

    @Builder
    private LocationSearchDto(Long mapId, String name, BigSubject bigSubject, String smallSubject) {
        this.mapId = mapId;
        this.name = name;
        this.bigSubject = bigSubject;
        this.smallSubject = smallSubject;
    }

    public static LocationSearchDto from(LocationSearch locationSearch) {
        BigSubject bigSubject;
        try {
            bigSubject = BigSubject.valueOf(locationSearch.getBigSubject());
        } catch (IllegalArgumentException e) {
            bigSubject = null;
        }

        return LocationSearchDto.builder()
                .mapId(locationSearch.getMapId())
                .name(locationSearch.getName())
                .bigSubject(bigSubject)
                .smallSubject(locationSearch.getSmallSubject())
                .build();
    }
}
