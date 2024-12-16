package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class FeignException extends BusinessException {

  public FeignException(ErrorCode errorCode) {
    super(errorCode);
  }

}