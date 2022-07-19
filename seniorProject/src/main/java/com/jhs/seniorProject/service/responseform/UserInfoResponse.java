package com.jhs.seniorProject.service.responseform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserInfoResponse {

    private String id;
    private String password;
    private String name;
    private List<MapInfoResponse> maps;
}
