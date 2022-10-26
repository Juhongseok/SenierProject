package com.jhs.seniorProject.service.responseform;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
@AllArgsConstructor
public class UserInfoResponse {

    private String id;
    private String password;
    private String name;
    private Page<UserMapList> maps;
}
