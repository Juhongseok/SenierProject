package com.jhs.seniorProject.service;

import com.jhs.seniorProject.controller.form.SaveLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationForm;
import com.jhs.seniorProject.controller.form.UpdateLocationSmallSubject;
import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.Map;
import com.jhs.seniorProject.repository.LocationRepository;
import com.jhs.seniorProject.repository.MapRepository;
import com.jhs.seniorProject.repository.SmallSubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {

    private final MapRepository mapRepository;
    private final LocationRepository locationRepository;
    private final SmallSubjectRepository smallSubjectRepository;

    public List<Location> locations(Long mapId) {
        return locationRepository.findByMapId(mapRepository.findById(mapId)
                        .orElseThrow(IllegalArgumentException::new));
    }
    public Location findLocation(Long locationId) {
        Location findLocation = locationRepository.findById(locationId)
                .orElseThrow(IllegalArgumentException::new);
        return findLocation;
    }

    public List<UpdateLocationSmallSubject> getSmallSubjectList(Map map) {
        return smallSubjectRepository.findByMap(map).stream()
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
                .map(locationForm.getMap())
                .userId(userId)
                .build();

        locationRepository.save(location);
    }

    public void updateLocation(Long locationId, UpdateLocationForm locationForm) {
        locationRepository.findById(locationId)
                .get()
                .chaneInfo(locationForm);
    }

}
