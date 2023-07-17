package com.example.dailycarebe.base.orm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractOnlyAuditingEntity implements Serializable {
    @Column(name = "created_date", updatable = false, nullable = false, columnDefinition = "DATETIME DEFAULT current_timestamp")
    private LocalDateTime createdDate;

    @Column(name = "created_by", updatable = false, nullable = false, length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'system'")
    private String createdBy;

    @Column(name = "last_modified_date", columnDefinition = "DATETIME DEFAULT current_timestamp")
    private LocalDateTime lastModifiedDate;

    @Column(name = "last_modified_by", length = 50, columnDefinition = "VARCHAR(50) DEFAULT 'system'")
    private String lastModifiedBy;

    @JsonIgnore
    @PrePersist
    private void onPersist() {
        LocalDateTime now = LocalDateTime.now();
        if (getCreatedDate() == null) {
            setCreatedDate(now);
        }

//        String currentUuid = SecurityContextUtil.getUserUuid();
//        if (SecurityContextUtil.ANONYMOUS.equals(currentUuid))
//            currentUuid = SYSTEM;
//
//        if (getCreatedBy() == null) {
//            setCreatedBy(currentUuid);
//        }

//        setLastModifiedBy(currentUuid);
        setLastModifiedDate(LocalDateTime.now());
    }

    @JsonIgnore
    @PreUpdate
    private void onUpdate() {
//        String currentUuid = SecurityContextUtil.getUserUuid();
//        if (SecurityContextUtil.ANONYMOUS.equals(currentUuid))
//            currentUuid = SYSTEM;
//        setLastModifiedBy(currentUuid);
        setLastModifiedDate(LocalDateTime.now());
    }
}
