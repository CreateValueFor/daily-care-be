package com.example.dailycarebe.oauth.social.kakao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class KakaoOauthRequestDto {

  private String grant_type;
  private String client_id;
  private String client_secret;
  private String redirect_uri;
  private String code;
}
