package com.jhs.seniorProject.service;

import com.jhs.seniorProject.controller.form.SaveLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationSmallSubject;
import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.domain.enumeration.Visibility;
import com.jhs.seniorProject.repository.LocationRepository;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.SmallSubjectRepository;
import com.jhs.seniorProject.service.requestform.LocationSearchDto;
import com.jhs.seniorProject.service.responseform.LocationList;
import com.jhs.seniorProject.controller.form.LocationSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

    private final MapRepository mapRepository;
    private final LocationRepository locationRepository;
    private final SmallSubjectRepository smallSubjectRepository;

    public List<LocationList> getLocations(Long mapId, String userId) {
        Map findMap = mapRepository.findByIdWithUserMap(mapId, userId);
        if (findMap.getUserMaps().get(0).getVisibility().equals(Visibility.CLOSE)) {
            return Collections.emptyList();
        }
        return locationRepository.findLocationsByMapId(findMap).stream()
                .map(location -> LocationList.builder()
                        .locationId(location.getId())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude())
                        .name(location.getName())
                        .build())
                .collect(toList());
    }

    public Location findLocation(Long locationId) {
        //TODO Entity -> DTO 변경
        Location findLocation = locationRepository.findById(locationId)
                .orElseThrow(IllegalArgumentException::new);
        return findLocation;
    }

    public List<UpdateLocationSmallSubject> getSmallSubjectList(Long mapId) {
        return smallSubjectRepository.findByMapId(mapId).stream()
                .map(UpdateLocationSmallSubject::new)
                .collect(toList());
    }

    public void saveLocation(SaveLocationForm locationForm, String userId) {
        Location location = Location.builder()
                .latitude(locationForm.getLatitude())
                .longitude(locationForm.getLongitude())
                .memo(locationForm.getMemo())
                .name(locationForm.getName())
                .bigSubject(locationForm.getBigSubject())
                .smallSubject(locationForm.getSmallSubject())
                .map(mapRepository.findById(locationForm.getMapId()).get())
                .userId(userId)
                .build();

        locationRepository.save(location);
    }

    public void updateLocation(Long locationId, UpdateLocationForm locationForm) {
        locationRepository.findById(locationId)
                .get()
                .chaneInfo(locationForm);
    }

    @Transactional(readOnly = true)
    public List<LocationList> findLocationWithCondV1(LocationSearch locationSearch) {
        return locationRepository.findLocationCond(LocationSearchDto.from(locationSearch)).stream()
                .map(LocationList::from)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<LocationList> findLocationWithCondV2(LocationSearch locationSearch) {
        return locationRepository.findLocationCondDto(LocationSearchDto.from(locationSearch));
    }
}
