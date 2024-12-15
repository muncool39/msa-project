package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class ProductStockException extends BusinessException {

	public ProductStockException(ErrorCode errorCode) {
		super(errorCode);
	}
}
