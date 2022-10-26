package com.jhs.seniorProject.service.requestform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendForm {
    @NotBlank(message = "아이디를 입력하세요")
    String id;
}