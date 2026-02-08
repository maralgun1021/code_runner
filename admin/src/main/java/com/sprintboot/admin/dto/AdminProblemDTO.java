package com.sprintboot.admin.dto;

import lombok.Data;

@Data
public class AdminProblemDTO {

    private String name;
    private String description;
    private String difficulty;
    private String testCase;
    private Long point;

}
