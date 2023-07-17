package com.example.dailycarebe.base.paging.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.dailycarebe.util.HashidsUtil;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PaginationRequestDto<CURSOR_TYPE> {
  private CURSOR_TYPE cursorType;

  private String cursor;

  private Integer page = 0;

  @NotNull
  private Integer count;

  private Boolean isDisplay;

  private Boolean isActive;

  @JsonIgnore
  public Long getCursorId() {
    if (cursor == null) {
      return null;
    } else {
      return HashidsUtil.decodeNumber(cursor);
    }
  }
}
