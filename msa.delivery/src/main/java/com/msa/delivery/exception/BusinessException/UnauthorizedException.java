package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class UnauthorizedException extends BusinessException{

  public UnauthorizedException(ErrorCode errorCode) {
    super(errorCode);
  }
}
