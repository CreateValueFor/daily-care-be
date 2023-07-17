package com.example.dailycarebe.user.auth.authorization.dto;

import com.example.dailycarebe.base.orm.dto.AbstractRegisterDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserAppRoleRegisterDto extends AbstractRegisterDto {
    private String userUuid;

    private String appRoleUuid;
}
