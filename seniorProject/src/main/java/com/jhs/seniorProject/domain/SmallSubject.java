package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.baseentity.TimeAndPersonInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MAP_SMALL_SUBJECT")
public class SmallSubject extends TimeAndPersonInfo {

    @Id @Column(name = "SMALL_SUBJECT_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "SMALL_SUBJECT")
    private String subjectName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MAP_ID")
    private Map map;

    public SmallSubject(String subjectName, Map map, String createdBy) {
        super(createdBy);
        this.subjectName = subjectName;
        this.map = map;
    }
}
