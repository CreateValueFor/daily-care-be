package com.example.dailycarebe.oauth.social.kakao;

import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.exception.InvalidDataRequestException;
import com.example.dailycarebe.oauth.social.kakao.dto.KakaoOauthRequestDto;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoUser;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoUserInfo;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoOauthService extends BaseService<User, UserRepository> {

  private final KakaoApiClient client;
  private final KakaoOauthClient oauthClient;
  InvalidDataRequestException AUTH_FAIL = new InvalidDataRequestException("잘못 된 요청입니다.");

  @Transactional
  public String getKaKaoAccessToken (String code, HttpServletRequest request) {
    String redirectUri = request.getHeader(HttpHeaders.ORIGIN)+"/auth/login/kakao";
    String access_Token = "";

    KakaoOauthRequestDto dto = new KakaoOauthRequestDto(
      "authorization_code",
      "30eb6c1ae057d0558f155d45faf774cd",
      "jJpYMa37DaPcgpaf2cANRF18Xuig8eux",
      redirectUri,
      code
    );
    ResponseEntity<String> response = null;

    try {
      response = oauthClient.getAccessToken(dto);

    } catch (Exception e) {
      e.printStackTrace();
    }
    JsonElement element = null;
    if (response != null) {
      element = JsonParser.parseString(Objects.requireNonNull(response.getBody()));
    } else {
      throw new InvalidDataRequestException("카카오 통신에러.");
    }
    access_Token = element.getAsJsonObject().get("access_token").getAsString();

    return access_Token;
  }

  public KakaoUser verifyToken(String token) throws InvalidDataRequestException {
    try {
      ResponseEntity<KakaoUser> response = client.verifyToken("Bearer " + token);

      if (response.getStatusCode() != HttpStatus.OK) throw AUTH_FAIL;

      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
      throw AUTH_FAIL;
    }
  }

  public KakaoUserInfo getUserInfo(String token) throws InvalidDataRequestException {
    try {
      ResponseEntity<KakaoUserInfo> response = client.getUserInfo("Bearer " + token);

      if (response.getStatusCode() != HttpStatus.OK) throw AUTH_FAIL;

      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
      throw AUTH_FAIL;
    }
  }
}
