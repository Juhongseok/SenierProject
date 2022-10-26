package com.jhs.seniorProject.controller.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MapForm {
    @NotBlank
    String name;
}