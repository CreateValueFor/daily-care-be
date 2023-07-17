package com.example.dailycarebe.oauth.social.kakao;

import org.springframework.beans.factory.annotation.Value;

public class KakaoOauthConfiguration {
  @Value("${oauth2.kakao.clientId}")
  private String clientId;

  @Value("${oauth2.kakao.secretKey}")
  private String secretKey;
}
