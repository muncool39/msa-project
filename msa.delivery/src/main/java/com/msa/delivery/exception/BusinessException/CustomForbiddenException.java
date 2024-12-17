package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class CustomForbiddenException extends BusinessException {

	public CustomForbiddenException() {
		super(ErrorCode.FORBIDDEN);
	}

	public CustomForbiddenException(String source) {
		super(ErrorCode.FORBIDDEN, source);
	}
}
