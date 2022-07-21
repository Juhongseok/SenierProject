package com.jhs.seniorProject.service.responseform;

import com.jhs.seniorProject.domain.enumeration.BigSubject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationSearch {
    private Long mapId;
    private String name;
    private BigSubject bigSubject;
    private String smallSubject;
}
