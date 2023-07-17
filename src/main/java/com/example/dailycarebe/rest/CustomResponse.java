package com.example.dailycarebe.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

@Data
public class CustomResponse<DATA> implements Serializable {
  @ApiModelProperty(required = true)
  private DATA data;

  private CustomError error;

  public static <DATA> ResponseEntity<CustomResponse<DATA>> ok(DATA data) {
    CustomResponse<DATA> customResponse = new CustomResponse<>();
    customResponse.setData(data);
    return ResponseEntity.ok(customResponse);
  }

  public static <DATA> ResponseEntity<CustomResponse<DATA>> error(HttpStatus httpStatus, String message) {
    CustomResponse<DATA> customResponse = new CustomResponse<>();
    CustomError error = new CustomError();
    error.setMessage(message);
    customResponse.setError(error);
    return ResponseEntity.status(httpStatus).body(customResponse);
  }

  @Data
  public static class CustomResponseError {
    private String message;
  }
}
