package com.example.dailycarebe.base.orm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(indexes = {
  @Index(name = "idx-status", columnList = "entityStatus")
})
@Audited(withModifiedFlag = true)
public abstract class AbstractAuditingEntity<ENTITY> extends AbstractPersistAuditingEntity<ENTITY> {
  @Column(name = "last_modified_date", columnDefinition = "DATETIME DEFAULT current_timestamp")
  private LocalDateTime lastModifiedDate;

  @Column(name = "last_modified_by", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'system'")
  private String lastModifiedBy;

  @Column
  private Long lastModifiedUserId;

  @JsonIgnore
  @PrePersist
  @PreUpdate
  private void injectUpdatedXXX() {
    setLastModifiedBy("SYSTEM");
    setLastModifiedUserId(-1L);
//        String currentUuid = SecurityContextUtil.getUserUuid();
//        if (SecurityContextUtil.ANONYMOUS.equals(currentUuid))
//            currentUuid = SYSTEM;
//
//        long currentUserId = SecurityContextUtil.getUserId();
//        if (SecurityContextUtil.ANONYMOUS_USER_ID == currentUserId)
//            currentUserId = SYSTEM_USER_ID;
//
//        if (isForceSaveBySystem()) {
//            setLastModifiedBy(SYSTEM);
//            setLastModifiedUserId(SYSTEM_USER_ID);
//        } else {
//            setLastModifiedBy(currentUuid);
//            setLastModifiedUserId(currentUserId);
//        }

    setLastModifiedDate(LocalDateTime.now());
  }

//    @JsonIgnore
//    public boolean isUserModified() {
//        return lastModifiedUserId != null && lastModifiedUserId != SYSTEM_USER_ID;
//    }

  @JsonIgnore
  protected void cloneTo(AbstractAuditingEntity entity) {
    entity.setLastModifiedBy(lastModifiedBy);
    entity.setLastModifiedDate(lastModifiedDate);
    entity.setLastModifiedUserId(lastModifiedUserId);
    super.cloneTo(entity);
  }
}
