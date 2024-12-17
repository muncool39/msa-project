package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class CustomBadRequestException extends BusinessException {

	public CustomBadRequestException() {
		super(ErrorCode.BAD_REQUEST);
	}

	public CustomBadRequestException(String source) {
		super(ErrorCode.BAD_REQUEST, source);
	}

}