package com.example.dailycarebe.rest;

import lombok.Data;

import java.io.Serializable;

@Data
public class CustomError implements Serializable {
  
  private String code;
  
  private String message;
}
