package com.example.dailycarebe.oauth.social.kakao.model;

import lombok.Data;

@Data
public class KakaoUserInfo {
  private long id;
  private KakaoProperties properties;
  private KakaoAccount kakao_account;
}
