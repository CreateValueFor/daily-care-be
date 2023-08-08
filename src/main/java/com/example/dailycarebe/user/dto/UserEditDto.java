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

  private String password;

  private String name;

  private String phone;

//  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birth;

  private UserGender gender;

  private String fcmToken;

  private Double height;
  private Double weight;

  private Integer drinkingFrequency;

  private Integer isSmoking;

  private Integer workOutFrequency;

  private Integer constipationFrequency;

  private Integer diarrheaFrequency;

  private Integer bloodStoolFrequency;

  private Integer mucusStoolFrequency;

  private Integer bowelMovementsDayTimeFrequency;

  private Integer bowelMovementsNightTimeFrequency;

  private Integer rectalPainFrequency;

  private Integer gasBloatingFrequency;

  private Integer gasLeakingFrequency;

  private Integer stoolLeakingFrequency;

  private Integer anusPainFrequency;

  private Integer panicFrequency;
}
