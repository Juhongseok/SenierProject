package com.jhs.seniorProject.repository.customRepository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import com.jhs.seniorProject.service.requestform.LocationSearchDto;
import com.jhs.seniorProject.service.responseform.LocationList;
import com.jhs.seniorProject.controller.form.LocationSearch;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.jhs.seniorProject.domain.QLocation.location;
import static com.jhs.seniorProject.domain.QMap.map;
import static com.jhs.seniorProject.domain.QSmallSubject.smallSubject;

@Slf4j
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Location> findLocationCond(LocationSearchDto locationSearch) {
//        return getLocationListV1(locationSearch);
        return getLocationListV2(locationSearch);
    }

    @Override
    public List<LocationList> findLocationCondDto(LocationSearchDto locationSearch) {
        return getLocationListV3(locationSearch);
    }

    private List<LocationList> getLocationListV1(LocationSearch locationSearch) {
        String query = "select new com.jhs.seniorProject.service.responseform.LocationList(l.id, l.latitude, l.longitude, l.name) " +
                        "from Location l " +
                        "join fetch l.map m " +
                        "where m.id=: mapId";

        boolean isBigSubject = false;
        boolean isSmallSubject = false;

        if (StringUtils.hasText(locationSearch.getBigSubject())) {
            query += " and l.bigSubject=:bigSubject";
            isBigSubject = true;
        }

        if (locationSearch.getSmallSubject() != null) {
            query += " and l.smallSubject.subjectName=:smallSubject";
            isSmallSubject = true;
        }

        TypedQuery<LocationList> typedQuery = em.createQuery(query, LocationList.class);

        if (isBigSubject) {
            typedQuery.setParameter("bigSubject", locationSearch.getBigSubject());
        }

        if (isSmallSubject) {
            typedQuery.setParameter("smallSubject", locationSearch.getSmallSubject());
        }
        return typedQuery
                .setParameter("mapId", locationSearch.getMapId())
                .getResultList();
    }

    private List<Location> getLocationListV2(LocationSearchDto locationSearch) {
        return query().selectFrom(location)
                .join(location.map, map).fetchJoin()
                .join(location.smallSubject, smallSubject).fetchJoin()
                .where(
                        mapIdEq(locationSearch.getMapId()),
                        locationSmallSubjectEq(locationSearch.getSmallSubject()),
                        locationBigSubjectEq(locationSearch.getBigSubject())
                )
                .fetch();
    }

    private List<LocationList> getLocationListV3(LocationSearchDto locationSearch) {
        return query().select(Projections.constructor(LocationList.class,
                        location.id.as("locationId"),
                        location.latitude,
                        location.longitude,
                        location.name
                ))
                .from(location)
                .join(location.map, map)
                .join(location.smallSubject, smallSubject)
                .where(
                        mapIdEq(locationSearch.getMapId()),
                        locationSmallSubjectEq(locationSearch.getSmallSubject()),
                        locationBigSubjectEq(locationSearch.getBigSubject())
                )
                .fetch();
    }

    private BooleanExpression mapIdEq(Long mapId) {
        return location.map.id.eq(mapId);
    }

    private BooleanExpression locationSmallSubjectEq(Long smallSubjectId) {
        return smallSubjectId != null ? location.smallSubject.id.eq(smallSubjectId) : null;
    }

    private BooleanExpression locationBigSubjectEq(BigSubject bigSubjectName) {
        return ObjectUtils.isEmpty(bigSubjectName) ? null : location.bigSubject.eq(bigSubjectName);
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(em);
    }
}
