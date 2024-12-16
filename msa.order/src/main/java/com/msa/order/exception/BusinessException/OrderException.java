package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class OrderException extends BusinessException {

	public OrderException(ErrorCode errorCode) {
		super(errorCode);
	}

}