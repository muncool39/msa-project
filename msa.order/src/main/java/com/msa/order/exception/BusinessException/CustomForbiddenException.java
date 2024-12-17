package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class CustomForbiddenException extends BusinessException {

	public CustomForbiddenException() {
		super(ErrorCode.FORBIDDEN);
	}

	public CustomForbiddenException(String source) {
		super(ErrorCode.FORBIDDEN, source);
	}
}