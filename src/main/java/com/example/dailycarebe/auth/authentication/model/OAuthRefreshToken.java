package com.example.dailycarebe.auth.authentication.model;

import com.example.dailycarebe.base.orm.model.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="oauth_refresh_token", indexes = {
		@Index(columnList = "token_id", unique = true, name = "uq1")
})
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OAuthRefreshToken extends AbstractEntity {

	@Column(name="token_id", length = 40, nullable = false)
	private String tokenId;
	
	@Lob
	@Column(name="token", columnDefinition = "mediumblob")
	private byte[] token;
	
	@Lob
	@Column(name="authentication", columnDefinition = "mediumblob")
	private byte[] authentication;
}
