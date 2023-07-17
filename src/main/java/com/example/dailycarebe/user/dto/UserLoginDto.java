package com.example.dailycarebe.user.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginDto implements Serializable {

  private String loginId;

  private String password;
}
