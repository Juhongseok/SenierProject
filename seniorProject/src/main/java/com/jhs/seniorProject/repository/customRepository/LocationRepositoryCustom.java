package com.jhs.seniorProject.repository.customRepository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.service.responseform.LocationList;
import com.jhs.seniorProject.service.responseform.LocationSearch;

import java.util.List;

public interface LocationRepositoryCustom {
    List<Location> findLocationCond(LocationSearch locationSearch);
    List<LocationList> findLocationCondDto(LocationSearch locationSearch);
}
