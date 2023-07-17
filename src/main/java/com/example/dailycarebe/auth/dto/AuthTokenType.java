package com.example.dailycarebe.auth.dto;

import lombok.Getter;

public enum AuthTokenType {
  BEARER("bearer");

  @Getter
  private final String type;

  AuthTokenType(String type) {
    this.type = type;
  }
}
