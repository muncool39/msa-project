package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  protected BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
