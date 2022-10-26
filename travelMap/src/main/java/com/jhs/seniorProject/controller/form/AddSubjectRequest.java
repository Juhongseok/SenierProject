package com.jhs.seniorProject.controller.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSubjectRequest {
    private Long mapId;
    private String subjectName;
}