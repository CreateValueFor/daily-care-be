package com.example.dailycarebe.base.orm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class AbstractPersistAuditingEntity<ENTITY> extends AbstractEntity<ENTITY> {
  @Column(name = "created_date", updatable = false, nullable = false, columnDefinition = "DATETIME DEFAULT current_timestamp")
  private LocalDateTime createdDate;

  @Column(name = "created_by", updatable = false, nullable = false, length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'system'")
  private String createdBy;

  @Column(name = "created_user_id", updatable = false, nullable = false, columnDefinition = "BIGINT DEFAULT 0")
  private Long createdUserId;

  @Transient
  @Builder.Default
  private boolean forceSaveBySystem = false;

  @JsonIgnore
  @PrePersist
  private void injectCreatedXXX() {
    LocalDateTime now = LocalDateTime.now();
    if (getCreatedDate() == null) {
      setCreatedDate(now);
    }

    setCreatedBy("SYSTEM");
    setCreatedUserId(-1L);
//        if (forceSaveBySystem) {
//            setCreatedBy(SYSTEM);
//            setCreatedUserId(SYSTEM_USER_ID);
//        }
//
//        if (getCreatedBy() == null) {
//            String currentUuid = SecurityContextUtil.getUserUuid();
//            if (ANONYMOUS.equals(currentUuid)) {
//                currentUuid = SYSTEM;
//            }
//            setCreatedBy(currentUuid);
//        }
//
//        if (getCreatedUserId() == null) {
//            long currentUserId = SecurityContextUtil.getUserId();
//            if (currentUserId == ANONYMOUS_USER_ID) {
//                currentUserId = SYSTEM_USER_ID;
//            }
//            setCreatedUserId(currentUserId);
//        }
  }

//    @JsonIgnore
//    public boolean isUserCreated() {
//        return createdUserId != null && createdUserId != SYSTEM_USER_ID;
//    }

  @JsonIgnore
  protected void cloneTo(AbstractPersistAuditingEntity entity) {
    entity.setCreatedBy(createdBy);
    entity.setCreatedDate(createdDate);
    entity.setCreatedUserId(createdUserId);
    super.cloneTo(entity);
  }
}
