package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class DeliveryNotFoundException extends BusinessException{

  public DeliveryNotFoundException() {
    super(ErrorCode.DELIVERY_NOT_FOUND);
  }
}
