package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;
import lombok.Getter;

@Getter
public abstract class BusinessException extends RuntimeException {

  private final ErrorCode errorCode;

  protected BusinessException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
}
