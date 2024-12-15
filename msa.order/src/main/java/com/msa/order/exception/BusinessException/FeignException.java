package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class FeignException extends BusinessException {

  public FeignException() {
    super(ErrorCode.EXTERNAL_SERVICE_ERROR);
  }

}