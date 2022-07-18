package com.jhs.seniorProject.service.responseform;

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
public class MapInfoAdmin implements MapInfoResponse{
    private String mapName;
    private String password;
    private List<UserInfo> userInfos = new ArrayList<>();

    @Builder
    public MapInfoAdmin(String mapName, String password) {
        this.mapName = mapName;
        this.password = password;
    }

    public void addUserInfo(UserMap userMap) {
        userInfos.add(new UserInfo(userMap.getId().getUserId(), userMap.getVisibility()));
    }

    @AllArgsConstructor
    private static class UserInfo{
        private String userId;
        private Visibility visibility;
    }
}
