package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class CustomFeignException extends BusinessException {

  public CustomFeignException() {
    super(ErrorCode.EXTERNAL_SERVICE_ERROR);
  }

  public CustomFeignException(String source) {
    super(ErrorCode.EXTERNAL_SERVICE_ERROR, source);
  }

}