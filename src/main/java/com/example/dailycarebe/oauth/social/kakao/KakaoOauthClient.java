package com.example.dailycarebe.oauth.social.kakao;

import com.example.dailycarebe.config.FeignDefaultConfig;
import com.example.dailycarebe.oauth.social.kakao.dto.KakaoOauthRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
  name = "kakaoOauthClient",
  url = "${feign.client.kakao.url.auth}",
  configuration = FeignDefaultConfig.class
)
public interface KakaoOauthClient {
  @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  ResponseEntity<String> getAccessToken(
    @RequestBody KakaoOauthRequestDto dto);
}
