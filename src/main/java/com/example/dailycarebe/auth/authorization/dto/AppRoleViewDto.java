package com.example.dailycarebe.auth.authorization.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode
@ToString
public class AppRoleViewDto {
    private String roleId;

    private String roleName;
}
