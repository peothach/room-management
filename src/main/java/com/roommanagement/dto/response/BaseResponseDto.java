package com.roommanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResponseDto<T> {
  private int status;
  private String message;
  private T data;

  public BaseResponseDto(int status, String message) {
    this.status = status;
    this.message = message;
  }
}
