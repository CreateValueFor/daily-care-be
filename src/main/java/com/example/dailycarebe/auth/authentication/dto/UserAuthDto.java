package com.example.dailycarebe.auth.authentication.dto;

import com.example.dailycarebe.auth.dto.AuthDto;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import com.example.dailycarebe.user.dto.UserViewDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserAuthDto extends AbstractAuditingDto {
  @ApiModelProperty(required = true)
  private UserViewDto user;

  @ApiModelProperty(required = true)
  private AuthDto auth;
}
