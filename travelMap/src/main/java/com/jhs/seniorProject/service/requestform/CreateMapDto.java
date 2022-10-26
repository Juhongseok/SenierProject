package com.jhs.seniorProject.service.requestform;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMapDto {
    private String mapName;
    private String userId;
}
