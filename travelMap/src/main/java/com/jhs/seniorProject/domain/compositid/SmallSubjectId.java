package com.jhs.seniorProject.domain.compositid;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
public class SmallSubjectId implements Serializable {

    @Column(name = "SMALL_SUBJECT")
    private String subjectName;

    @Column(name = "USER_ID")
    private String userId;
}
