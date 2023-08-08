package com.example.dailycarebe.auth.config;

import com.example.dailycarebe.auth.authentication.util.JWTTokenUtil;
import com.example.dailycarebe.config.hashid.HashidsProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableConfigurationProperties(HashidsProperties.class)
@EnableWebSecurity
class SecurityConfig {

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
      .antMatchers(HttpMethod.OPTIONS, "/**")
      .antMatchers(HttpMethod.POST
        , "/api/v1/users"
        , "/api/v1/users/**"
        , "/api/v1/users/action/login"
        , "/api/v1/auth/kakao"
      )
      .antMatchers(HttpMethod.GET
        , "/api/v1/health"
        , "/api/v1/action/convert-hash"
        , "/api/v1/action/convert-id"
        , "/api/v1/users/age"
      )
      ;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    Map<String, PasswordEncoder> encoders = new HashMap<>();
    encoders.put("bcrypt", new BCryptPasswordEncoder());
    encoders.put("sha256",
      new MessageDigestPasswordEncoder("SHA-256"));
//    encoders.put("sha256", new org.springframework.security.crypto.password.StandardPasswordEncoder());
    encoders.put("MD5", new MessageDigestPasswordEncoder("MD5"));

    return new DelegatingPasswordEncoder("sha256", encoders);
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    SecretKeySpec secretKey = new SecretKeySpec(JWTTokenUtil.JWT_SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
    return NimbusJwtDecoder.withSecretKey(secretKey).build();
  }
}
