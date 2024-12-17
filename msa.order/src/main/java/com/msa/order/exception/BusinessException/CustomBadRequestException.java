package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class CustomBadRequestException extends BusinessException {

	public CustomBadRequestException() {
		super(ErrorCode.BAD_REQUEST);
	}

	public CustomBadRequestException(String source) {
		super(ErrorCode.BAD_REQUEST, source);
	}

}