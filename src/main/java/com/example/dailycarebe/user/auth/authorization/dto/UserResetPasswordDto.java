package com.example.dailycarebe.user.auth.authorization.dto;

import com.example.dailycarebe.base.orm.dto.AbstractFetchDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserResetPasswordDto extends AbstractFetchDto {
  private String loginId;
  private String phone;
  private String name;
}
