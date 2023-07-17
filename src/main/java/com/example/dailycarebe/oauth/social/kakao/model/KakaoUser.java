package com.example.dailycarebe.oauth.social.kakao.model;

import lombok.Data;

@Data
public class KakaoUser {
  private long id;
  private int expires_in;
  private int app_id;

  public String getId() {
    return String.valueOf(id);
  }
}
