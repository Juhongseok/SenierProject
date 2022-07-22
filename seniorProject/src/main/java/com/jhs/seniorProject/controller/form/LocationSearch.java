package com.jhs.seniorProject.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationSearch {
    private Long mapId;
    private String name;
    private String bigSubject;
    private Long smallSubject;
}
