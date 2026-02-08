package com.sprintboot.admin.dto;

import lombok.Data;

@Data
public class AdminUserDTO {
    private String name;
    private String phone;
    private Long age;
    private String email;
    private Long role;
}
