package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class OrderNotFoundException extends BusinessException {

	public OrderNotFoundException() {
		super(ErrorCode.ORDER_NOT_FOUND);
	}

}