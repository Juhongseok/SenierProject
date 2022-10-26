package com.jhs.seniorProject.service.responseform;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
public class MapInfo {
    private Long mapId;
    private String mapName;
    private String password;
    private Page<SmallSubjectInfo> smallSubjects;
    private Page<UserInfo> userInfos;

    @Builder
    public MapInfo(Long mapId, String mapName, String password, Page<SmallSubjectInfo> smallSubjects, Page<UserInfo> userInfos) {
        this.mapId = mapId;
        this.mapName = mapName;
        this.password = password;
        this.smallSubjects = smallSubjects;
        this.userInfos = userInfos;
    }
}
