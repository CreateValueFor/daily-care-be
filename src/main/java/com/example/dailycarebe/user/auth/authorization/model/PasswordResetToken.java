package com.example.dailycarebe.user.auth.authorization.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Getter
@Setter

public class PasswordResetToken extends AbstractAuditingEntity {
  private static final int EXPIRATION = 60 * 24;

  private String token;

  @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  private User user;

  @Column(nullable = false)
  private LocalDateTime expiredDateTime;

  @PrePersist
  public void onPasswordResetTokenPrePersist(){
    this.expiredDateTime = LocalDateTime.now().plusMinutes(EXPIRATION);
  }

  public boolean isValidToken() {
    return DateUtil.isBeforeOrEqual(expiredDateTime, LocalDateTime.now());
  }
}
