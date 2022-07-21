package com.jhs.seniorProject.repository.customRepository;

import com.jhs.seniorProject.domain.Location;
import com.jhs.seniorProject.domain.enumeration.BigSubject;
import com.jhs.seniorProject.service.responseform.LocationList;
import com.jhs.seniorProject.service.responseform.LocationSearch;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

import static com.jhs.seniorProject.domain.QLocation.location;
import static com.jhs.seniorProject.domain.QMap.map;
import static com.jhs.seniorProject.domain.QSmallSubject.smallSubject;

@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Location> findLocationCond(LocationSearch locationSearch) {
//        return getLocationListV1(locationSearch);
        return getLocationListV2(locationSearch);
    }

    @Override
    public List<LocationList> findLocationCondDto(LocationSearch locationSearch) {
        return getLocationListV3(locationSearch);
    }

    private List<LocationList> getLocationListV1(LocationSearch locationSearch) {
//        String query = "select l from Location l join fetch l.map m where m.id=: mapId";
        String query = "select new com.jhs.seniorProject.service.responseform.LocationList(l.id, l.latitude, l.longitude, l.name) " +
                        "from Location l " +
                        "join fetch l.map m " +
                        "where m.id=: mapId";

        boolean isName = false;
        boolean isBigSubject = false;
        boolean isSmallSubject = false;

        if (StringUtils.hasText(locationSearch.getName())) {
            query += " and l.name=:name";
            isName = true;
        }

        if (StringUtils.hasText(locationSearch.getBigSubject().name())) {
            query += " and l.bigSubject=:bigSubject";
            isBigSubject = true;
        }

        if (StringUtils.hasText(locationSearch.getSmallSubject())) {
            query += " and l.smallSubject.subjectName=:smallSubject";
            isSmallSubject = true;
        }

        TypedQuery<LocationList> typedQuery = em.createQuery(query, LocationList.class);

        if (isName) {
            typedQuery.setParameter("name", locationSearch.getName());
        }

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

    private List<Location> getLocationListV2(LocationSearch locationSearch) {
        return query().selectFrom(location)
                .join(location.map, map).fetchJoin()
                .join(location.smallSubject, smallSubject).fetchJoin()
                .where(
                        mapIdEq(locationSearch.getMapId()),
                        locationSmallSubjectEq(locationSearch.getSmallSubject()),
                        locationNameEq(locationSearch.getName()),
                        locationBigSubjectEq(locationSearch.getBigSubject())
                )
                .fetch();
    }

    private List<LocationList> getLocationListV3(LocationSearch locationSearch) {
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
                        locationNameEq(locationSearch.getName()),
                        locationBigSubjectEq(locationSearch.getBigSubject())
                )
                .fetch();
    }

    private BooleanExpression mapIdEq(Long mapId) {
        return location.map.id.eq(mapId);
    }

    private BooleanExpression locationNameEq(String locationName) {
        return location.name.eq(locationName);
    }

    private BooleanExpression locationSmallSubjectEq(String smallSubjectName) {
        return location.smallSubject.subjectName.eq(smallSubjectName);
    }

    private BooleanExpression locationBigSubjectEq(BigSubject bigSubjectName) {
        return location.bigSubject.eq(bigSubjectName);
    }

    private JPAQueryFactory query() {
        return new JPAQueryFactory(em);
    }
}
