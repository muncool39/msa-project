package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;
  private String source; // feignClient 예외처리를 위함.

  protected BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  protected BusinessException(ErrorCode errorCode, String source) {
    super(source + errorCode.getMessage());
    this.errorCode = errorCode;
    this.source = source;
  }
}
