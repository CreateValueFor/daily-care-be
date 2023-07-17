package com.example.dailycarebe.auth.config;

import com.example.dailycarebe.auth.authentication.AppUserDetailsService;
import com.example.dailycarebe.config.SwaggerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@Order(1)
class UserSecurityConfig {

  private final AppUserDetailsService userDetailsService;

  private final PasswordEncoder passwordEncoder;

  private final UserJwtAuthorityConverter jwtAuthorityConverter;

  @Bean("userFilterChain")
  @Profile({"prod", "qa", "stage"})
  @Order(2)
  public SecurityFilterChain filterChain(HttpSecurity http, @Qualifier("userAuthenticationProvider") AuthenticationProvider authenticationProvider) throws Exception {
    HttpSecurity httpSecurity = makeDefaultHttpSecurity(http, authenticationProvider);

    httpSecurity
      .requestMatchers().antMatchers("/api/v1/**").antMatchers("/open/**")
      .and()
      .authorizeHttpRequests((authz) -> authz
        .antMatchers("/api/v1/open/**").permitAll()
        .anyRequest().authenticated()
      );
    return http.build();
  }

  @Bean("userFilterChain")
  @Order(2)
  public SecurityFilterChain filterChainDev(HttpSecurity http, @Qualifier("userAuthenticationProvider") AuthenticationProvider authenticationProvider) throws Exception {
    HttpSecurity httpSecurity = makeDefaultHttpSecurity(http, authenticationProvider);
    httpSecurity
      .requestMatchers().antMatchers("/api/v1/**").antMatchers("/open/**")
      .and()
      .authorizeHttpRequests((authz) -> authz
        .antMatchers("/api/v1/open/**").permitAll()
        .antMatchers(SwaggerConfig.SWAGGER_AUTH_WHITELIST).permitAll()
        .anyRequest().authenticated()
      );
    return http.build();
  }

  private HttpSecurity makeDefaultHttpSecurity(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {
    AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
      .userDetailsService(userDetailsService)
      .passwordEncoder(passwordEncoder)
      .and()
      .build();

    return http
      .csrf().disable()
      .authenticationManager(authenticationManager)
      .oauth2ResourceServer((oauth2) -> oauth2
        .jwt()
        .jwtAuthenticationConverter(jwtAuthorityConverter))
      .httpBasic(withDefaults());
  }

  @Bean("userAuthenticationProvider")
  public DaoAuthenticationProvider userAuthenticationProvider(PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }
}
