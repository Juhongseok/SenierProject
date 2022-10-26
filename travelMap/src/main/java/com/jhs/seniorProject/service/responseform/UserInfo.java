package com.jhs.seniorProject.service.responseform;

import com.jhs.seniorProject.domain.enumeration.Visibility;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfo {
    private String userId;
    private Visibility visibility;
}