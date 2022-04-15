package com.jhs.seniorProject.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MAP_SMALL_SUBJECT")
public class SmallSubject {

    @Id @Column(name = "SMALL_SUBJECT_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "SMALL_SUBJECT")
    private String subjectName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "MAP_ID")
    private Map map;
}
