package com.example.dailycarebe.config;

import feign.Logger;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@RequiredArgsConstructor
public class FeignDefaultConfig {

  private final ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  public Encoder formEncoder()
  {
    return new FormEncoder(new SpringEncoder(this.messageConverters));
  }

  @Bean
  @Profile("!prod")
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }
}
