package com.msa.order.exception.BusinessException;

import com.msa.order.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {

	public UnauthorizedException() {
		super(ErrorCode.UNAUTHORIZED);
	}

	public UnauthorizedException(String source) {
		super(ErrorCode.UNAUTHORIZED, source);
	}
}
