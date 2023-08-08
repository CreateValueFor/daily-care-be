package com.example.dailycarebe.user.dto;

import com.example.dailycarebe.user.model.*;
import com.example.dailycarebe.base.orm.dto.AbstractAuditingDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@RequiredArgsConstructor
public class UserViewDto extends AbstractAuditingDto {
  private String email;
  private String phone;
  private String name;
  private LocalDate birth;
  private UserGender gender;

  private String loginId;

  private UserStateType state;

  private ProviderType provider;

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
