package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class NotFoundException extends BusinessException {

	public NotFoundException() {
		super(ErrorCode.NOT_FOUND);
	}

	public NotFoundException(String source) {
		super(ErrorCode.NOT_FOUND, source);
	}
}
