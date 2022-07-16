package com.jhs.seniorProject.service.requestform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AddMapDto {
    private String createUserId;
    private String password;
    private String addUserId;
}
