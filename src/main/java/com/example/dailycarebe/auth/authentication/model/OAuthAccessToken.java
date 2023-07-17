package com.example.dailycarebe.auth.authentication.model;

import com.example.dailycarebe.base.orm.model.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "oauth_access_token", indexes = {
  @Index(columnList = "token_id", unique = true, name = "uq1"),
  @Index(columnList = "user_name", name = "idx1")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OAuthAccessToken extends AbstractEntity {

    @Column(name = "token_id", length = 40)
    private String tokenId;

    @Lob
    @Column(name = "token", columnDefinition = "mediumblob")
    private byte[] token;

    @Column(name = "authentication_id", length = 40)
    private String authenticationId;

    @Column(name = "user_name", length = 50)
    private String userName;

    @Column(name = "client_id")
    private String clientId;

    @Lob
    @Column(name = "authentication", columnDefinition = "mediumblob")
    private byte[] authentication;

    @Column(name = "refresh_token", length = 40)
    private String refreshToken;
}
