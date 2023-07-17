package com.example.dailycarebe.oauth.social.kakao;

import com.example.dailycarebe.oauth.social.kakao.model.KakaoUser;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
  name = "kakaoApiClient",
  url = "${feign.client.kakao.url.api}"
)
public interface KakaoApiClient {
  @GetMapping("/v1/user/access_token_info")
  ResponseEntity<KakaoUser> verifyToken(@RequestHeader(value = "Authorization") String Token);

  @GetMapping("/v2/user/me")
  ResponseEntity<KakaoUserInfo> getUserInfo(@RequestHeader(value = "Authorization") String Token);

}
