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
@Table(name = "oauth_client_token")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OAuthClientToken extends AbstractAuditingEntity {

    @Column(name = "token_id")
    private String tokenId;

    @Lob
    @Column(name = "token", columnDefinition = "mediumblob")
    private byte[] token;

    @Column(name = "authentication_id")
    private String authenticationId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "client_id")
    private String clientId;
}

