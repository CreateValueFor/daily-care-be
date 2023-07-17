package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.user.model.UserGender;
import com.example.dailycarebe.base.orm.dto.AbstractEditDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserEditDto extends AbstractEditDto {

  @Email(message = "이메일 형식이 잘못되었습니다.")
  private String email;

  private String password;

  private String name;

  private String phone;

//  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  private UserGender gender;

  private String fcmToken;
}
