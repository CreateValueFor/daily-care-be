package com.example.dailycarebe.error.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Builder
public class ErrorLog extends AbstractAuditingEntity {
    private Long userId;

    @Column(length = 50, nullable = false)
    private String app;

    @Lob
    private String message;

    @Lob
    private String log;

    @Column(length = 2000)
    private String requestUri;

    @Column(length = 10)
    private String requestMethod;

    @Lob
    private String userAgentDetail;

    @Lob
    private String header;

    @Column(length = 2000)
    private String queryParam;

    @Lob
    private String body;

    @Lob
    private String userInfo;

    @Lob
    private String context;

    @Tolerate
    public ErrorLog() {}
}
