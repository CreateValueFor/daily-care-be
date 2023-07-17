package com.example.dailycarebe.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public static final String[] SWAGGER_AUTH_WHITELIST = {
    "/v2/api-docs",
    "/swagger-resources",
    "/swagger-resources/**",
    "/configuration/ui",
    "/configuration/security",
    "/swagger-ui.html",
    "/webjars/**",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    "/configuration/**"
  };

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.OAS_30)
      .securityContexts(Collections.singletonList(securityContext()))
      .securitySchemes(Collections.singletonList(apiKey()))
//      .useDefaultResponseMessages(false)
      .select()
      .apis(RequestHandlerSelectors.basePackage("com.example.dailycarebe"))
//      .apis(RequestHandlerSelectors.any())
//      .paths(PathSelectors.any())
      .paths(PathSelectors.ant("/api/**"))
      .build()
      .apiInfo(apiInfo())
      .directModelSubstitute(LocalTime.class, String.class)
      ;
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
      .securityReferences(defaultAuth())
      .build();
  }
  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
  }
  private ApiKey apiKey() {
    return new ApiKey("Authorization", "Authorization", "header");
  }
  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("Swagger")
      .description("swagger config")
      .version("1.0")
      .build();
  }
}
