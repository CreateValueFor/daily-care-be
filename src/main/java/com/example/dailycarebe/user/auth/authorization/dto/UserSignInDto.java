package com.example.dailycarebe.user.auth.authorization.dto;

import com.example.dailycarebe.base.orm.dto.AbstractFetchDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserSignInDto extends AbstractFetchDto {

  @NotNull
  private String email;

  @NotNull
  private String password;

}
