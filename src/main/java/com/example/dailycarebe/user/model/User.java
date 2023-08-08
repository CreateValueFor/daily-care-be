package com.example.dailycarebe.user.model;

import com.example.dailycarebe.app.exercise.model.CourseType;
import com.example.dailycarebe.app.exercise.model.CourseWeekType;
import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.auth.authorization.model.UserAppRole;
import com.example.dailycarebe.util.HashidsUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class User extends AbstractAuditingEntity {

  private String loginId;

  private String email;

  private String name;

  private String password;

  private String phone;

  private LocalDate birth;

  @Enumerated(EnumType.STRING)
  private UserGender gender;

  @Column(columnDefinition = "VARCHAR(40) DEFAULT 'LOCAL'")
  @Enumerated(EnumType.STRING)
  private ProviderType provider;

  @Column
  private String providerId;

  @Column(columnDefinition = "VARCHAR(40) DEFAULT 'ACTIVE'")
  @Enumerated(EnumType.STRING)
  private UserStateType state;

  @Column
  private String fcmToken;

  @Column
  @Enumerated(EnumType.STRING)
  private UserExerciseType userExerciseType;

  @Column
  @Enumerated(EnumType.STRING)
  private CourseWeekType courseWeekType;

  @Column
  private Integer courseWeek;

  @Column
  private Integer courseDay;

  private Boolean isCourseUpgradable;

  private LocalDate startDate;

  private LocalDate nextWeek;

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  @JsonIgnore
  @Builder.Default
  private Set<UserAppRole> roles = new HashSet<>();

  @Column
  private Double height;

  @Column
  private Double weight;

  @Column
  private Integer drinkingFrequency;

  @Column
  private Integer isSmoking;

  @Column
  private Integer workOutFrequency;

  @Column
  private Integer constipationFrequency;

  @Column
  private Integer diarrheaFrequency;

  @Column
  private Integer bloodStoolFrequency;

  @Column
  private Integer mucusStoolFrequency;

  @Column
  private Integer bowelMovementsDayTimeFrequency;

  @Column
  private Integer bowelMovementsNightTimeFrequency;

  @Column
  private Integer rectalPainFrequency;

  @Column
  private Integer gasBloatingFrequency;

  @Column
  private Integer gasLeakingFrequency;

  @Column
  private Integer stoolLeakingFrequency;

  @Column
  private Integer anusPainFrequency;

  @Column
  private Integer panicFrequency;

  public static User of(String uuid) {
    User user = new User();
    user.setId(HashidsUtil.decodeNumber(uuid));
    return user;
  }

  public static User of(long id) {
    User user = new User();
    user.setId(id);
    return user;
  }
}
