package com.jhs.seniorProject.repository.customRepository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.service.requestform.LocationSearchDto;
import com.jhs.seniorProject.service.responseform.LocationList;

import java.util.List;

public interface LocationRepositoryCustom {
    List<Location> findLocationCond(LocationSearchDto locationSearch);
    List<LocationList> findLocationCondDto(LocationSearchDto locationSearch);
}
