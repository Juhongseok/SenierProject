package com.jhs.seniorProject.service.requestform;

import com.jhs.seniorProject.controller.form.AddSubjectRequest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddSubjectDto {
    private String userId;
    private Long mapId;
    private String subjectName;

    public static AddSubjectDto from(AddSubjectRequest request, String userId) {
        return AddSubjectDto.builder()
                .userId(userId)
                .mapId(request.getMapId())
                .subjectName(request.getSubjectName())
                .build();
    }
}
