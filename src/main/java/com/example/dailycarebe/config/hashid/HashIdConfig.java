package com.example.dailycarebe.config.hashid;

import com.example.dailycarebe.hash.HashIds;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(HashidsProperties.class)
public class HashIdConfig {

  @Bean
  public HashIds hashIds(HashidsProperties hashidsProperties) {
    return new HashIds(
      hashidsProperties.getSalt(),
      hashidsProperties.getAlphabet(),
      hashidsProperties.getSeps(),
      hashidsProperties.getMinLength());
  }
}
