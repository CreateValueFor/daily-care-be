package com.example.dailycarebe.user.auth.authorization.dto;

import com.example.dailycarebe.auth.authorization.dto.AppRoleViewDto;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import com.example.dailycarebe.user.dto.UserViewDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserAppRoleViewDto extends AbstractAuditingDto {
    private UserViewDto user;

    private AppRoleViewDto appRole;
}
