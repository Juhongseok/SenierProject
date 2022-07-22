package com.jhs.seniorProject.service.responseform;

import com.jhs.seniorProject.domain.SmallSubject;
import com.jhs.seniorProject.domain.UserMap;
import com.jhs.seniorProject.domain.enumeration.Visibility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MapInfo {
    private Long mapId;
    private String mapName;
    private String password;
    private List<SmallSubjectInfo> smallSubjects = new ArrayList<>();
    private List<UserInfo> userInfos = new ArrayList<>();

    @Builder
    public MapInfo(Long mapId, String mapName, String password) {
        this.mapId = mapId;
        this.mapName = mapName;
        this.password = password;
    }

    public void addUserInfo(UserMap userMap) {
        userInfos.add(new UserInfo(userMap.getId().getUserId(), userMap.getVisibility()));
    }

    public void addSmallSubject(SmallSubject smallSubject) {
        smallSubjects.add(new SmallSubjectInfo(smallSubject.getId(), smallSubject.getSubjectName()));
    }

    @Data
    @AllArgsConstructor
    private static class UserInfo{
        private String userId;
        private Visibility visibility;
    }
}
