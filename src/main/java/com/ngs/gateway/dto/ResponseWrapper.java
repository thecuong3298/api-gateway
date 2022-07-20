package com.ngs.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

  private String code;

  private String message;

  private T data;

  public ResponseWrapper(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
