package com.example.dailycarebe.auth.authentication.model;

import com.example.dailycarebe.base.orm.model.AbstractAuditingEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "oauth_code")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OAuthCode extends AbstractAuditingEntity {

    @Column(name = "code")
    private String code;

    @Lob
    @Column(name = "authentication", columnDefinition = "mediumblob")
    private byte[] authentication;
}
