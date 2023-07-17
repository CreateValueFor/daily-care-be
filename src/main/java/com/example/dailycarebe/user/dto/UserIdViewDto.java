package com.example.dailycarebe.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@RequiredArgsConstructor
public class UserIdViewDto {
  private String maskedLoginId;

  private LocalDate createdDate;
}
