package com.example.dailycarebe.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AuthDto implements Serializable {

  private AuthTokenType tokenType;

  @JsonProperty("username")
  private String userName;

  private String accessToken;

  private String refreshToken;

  public String getTokenType() {
    return tokenType.getType();
  }
}
