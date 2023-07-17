package com.example.dailycarebe.oauth;

import com.example.dailycarebe.auth.authentication.dto.UserAuthDto;
import com.example.dailycarebe.base.BaseService;
import com.example.dailycarebe.exception.InvalidDataRequestException;
import com.example.dailycarebe.oauth.social.kakao.KakaoOauthService;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoAccount;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoProperties;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoUser;
import com.example.dailycarebe.oauth.social.kakao.model.KakaoUserInfo;
import com.example.dailycarebe.user.UserService;
import com.example.dailycarebe.user.mapper.UserMapper;
import com.example.dailycarebe.user.model.ProviderType;
import com.example.dailycarebe.user.model.User;
import com.example.dailycarebe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class OauthService extends BaseService<User, UserRepository> {

  private final UserService userService;

  private final UserMapper userMapper;

  private final KakaoOauthService kakaoOauthService;

  public UserAuthDto authSocialUser(@NotBlank String token, @NotNull ProviderType provider) throws InvalidKeySpecException, NoSuchAlgorithmException {
    User socialUser = null;
    UserAuthDto userAuthDto = new UserAuthDto();

    if (provider == ProviderType.KAKAO) {
      socialUser = getKakaoSocialUser(token);
    } else if (provider == ProviderType.APPLE) {
      // 추후 apple 로그인 로직 추가 예정
    } else {
      throw new InvalidDataRequestException("지원하지 않는 소셜 로그인 타입 입니다.");
    }

    userAuthDto.setUser(userMapper.entityToDto(socialUser));
    userAuthDto.setAuth(userService.createSocialUserAuthDto(socialUser.getLoginId()));

    return userAuthDto;
  }

  private User getKakaoSocialUser(String token) {
    KakaoUser user = kakaoOauthService.verifyToken(token);
    User socialUser = userService.getSocialUser(ProviderType.KAKAO, user.getId());

    if (socialUser == null) {
      KakaoUserInfo info = kakaoOauthService.getUserInfo(token);
      KakaoAccount account = info.getKakao_account();
      KakaoProperties properties = info.getProperties();

      socialUser = userService.registerSocialUser(user.getId(), ProviderType.KAKAO, account, properties);
    }

    return socialUser;
  }
}
