package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.base.orm.dto.AbstractRegisterDto;
import com.example.dailycarebe.user.model.UserGender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRegisterDto extends AbstractRegisterDto {

  @ApiModelProperty(example = "test")
  @NotNull(message = "아이디는 필수 입력값 입니다.")
  private String loginId;

  @ApiModelProperty(example = "email")
  @NotNull(message = "이메일은 필수 입력값 입니다.")
  @Email(message = "이메일 형식에 맞게 입력해 주세요.")
  private String email;

  @ApiModelProperty(example = "password")
  @NotNull(message = "패스워드는 필수 입력값 입니다.")
  private String password;

  @ApiModelProperty(example = "user")
  @NotNull(message = "이름은 필수 입력값 입니다.")
  private String name;

  @ApiModelProperty(example = "01085711175")
  @NotNull(message = "핸드폰 번호는 필수 입력값 입니다.")
  private String phone;

//  @ApiModelProperty(example = "1999-11-11")
  @NotNull(message = "생일 정보는 필수 입력값 입니다.")
//  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  @ApiModelProperty(example = "MALE")
  @NotNull(message = "성별 정보는 필수 입력값 입니다.")
  private UserGender gender;
}
