package com.jhs.seniorProject.domain;

import com.jhs.seniorProject.domain.compositid.SmallSubjectId;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "USER_SMALL_SUBJECT")
public class SmallSubject {

    /*@EmbeddedId
    private SmallSubjectId id;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;*/

    @Id @Column(name = "USER_SMALL_SUBJECT_ID")
    @GeneratedValue
    private Long id;

    @Column(name = "SMALL_SUBJECT")
    private String subjectName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
