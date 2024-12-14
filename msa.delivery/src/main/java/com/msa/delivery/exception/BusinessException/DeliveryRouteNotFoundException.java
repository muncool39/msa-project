package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class DeliveryRouteNotFoundException extends BusinessException{

  public DeliveryRouteNotFoundException() {
    super(ErrorCode.DELIVERY_ROUTE_NOT_FOUND);
  }
}
