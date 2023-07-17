package com.example.dailycarebe.auth.config;

import com.example.dailycarebe.auth.authentication.AppUserDetails;
import com.example.dailycarebe.auth.authentication.model.AppUserDetailsType;
import com.example.dailycarebe.user.auth.authorization.UserAppRoleService;
import com.example.dailycarebe.user.repository.UserRepository;
import com.example.dailycarebe.util.HashidsUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class UserJwtAuthorityConverter implements Converter<Jwt, AbstractAuthenticationToken> {
  private final JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

  private final UserAppRoleService userAppRoleService;

  private final UserRepository userRepository;

  @Override
  public AbstractAuthenticationToken convert(Jwt jwt) {
    OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER, jwt.getTokenValue(), jwt.getIssuedAt(), jwt.getExpiresAt());
    Map<String, Object> attributes = jwt.getClaims();
    AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);

    String userUuid = (String) attributes.get("userUuid");
    AppUserDetailsType type = AppUserDetailsType.valueOf((String) attributes.get("type"));
    long userId = HashidsUtil.decodeNumber(userUuid);

    String name = token.getName();

    Collection<GrantedAuthority> authorities;
    if (type == AppUserDetailsType.USER) {
//      User user = userRepository.findByLoginId(name).get();
      authorities = userAppRoleService.getAppRolesByUserId(userId);
    }

    authorities = userAppRoleService.getAppRolesByUserId(userId);

//    Collection<GrantedAuthority> authorities = token.getAuthorities();
//    OAuth2AuthenticatedPrincipal principal = new DefaultOAuth2AuthenticatedPrincipal(attributes, authorities);
    AppUserDetails principal = new AppUserDetails(attributes, authorities);
    principal.setUserUuid(userUuid);

    return new BearerTokenAuthentication(principal, accessToken, authorities);
  }
}
