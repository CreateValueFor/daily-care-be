package com.example.dailycarebe.oauth;

import com.example.dailycarebe.auth.authentication.dto.UserAuthDto;
import com.example.dailycarebe.base.BaseComponent;
import com.example.dailycarebe.oauth.social.kakao.KakaoOauthService;
import com.example.dailycarebe.rest.CustomResponse;
import com.example.dailycarebe.user.model.ProviderType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class OauthController extends BaseComponent {

  private final KakaoOauthService kakaoOauthService;

  private final OauthService oauthService;


  @PostMapping("/kakao")
  public ResponseEntity<CustomResponse<UserAuthDto>> kakaoCallback(String token, HttpServletRequest request) throws InvalidKeySpecException, NoSuchAlgorithmException {
//    String token = kakaoOauthService.getKaKaoAccessToken(code, request);
    return CustomResponse.ok(oauthService.authSocialUser(token, ProviderType.KAKAO));
  }

}
