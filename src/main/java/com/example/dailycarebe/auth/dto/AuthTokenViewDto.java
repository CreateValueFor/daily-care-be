package com.example.dailycarebe.auth.dto;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Getter
@Setter
@RequiredArgsConstructor
public class AuthTokenViewDto implements Serializable {
//  @Getter(onMethod_ = {@JsonGetter("access_token")})
  private String access_token;
//  @Getter(onMethod_ = {@JsonGetter("refresh_token")})
  private String refresh_token;
  private String userUuid;
  private String loginId;
  private Boolean needRegister;

//  @JsonIgnore
//  public static AuthTokenViewDto mapper(OAuth2AccessToken token) {
//    AuthTokenViewDto authToken = new AuthTokenViewDto();
//    authToken.setAccess_token(token.getValue());
//    authToken.setRefresh_token(token.getRefreshToken().getValue());
//
//    Map<String, Object> map =  token.getAdditionalInformation();
//    authToken.setEmail((String) map.get("email"));
//    authToken.setUserUuid((String) map.get("userUuid"));
//
//    String username = (String) map.get("username");
//    authToken.setNeedRegister(username == null || username.isEmpty());
//
//    return authToken;
//  }
}
