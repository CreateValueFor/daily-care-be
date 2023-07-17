package com.example.dailycarebe.base.orm.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@ToString(callSuper = true)
public abstract class AbstractAuditingDto extends AbstractDto {
  private LocalDateTime createdDate;

  private String createdBy;

  @JsonIgnore
  @Getter
  private Long createdUserId;

  private LocalDateTime lastModifiedDate;

  private String lastModifiedBy;

  @JsonIgnore
  @Getter
  private Long lastModifiedUserId;

  private Boolean isActive;

  private Boolean isDisplay;

  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public LocalDateTime getLastModifiedDate() {
    return lastModifiedDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }
}
