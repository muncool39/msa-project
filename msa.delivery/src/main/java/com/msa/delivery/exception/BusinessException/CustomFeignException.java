package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class CustomFeignException extends BusinessException {

	public CustomFeignException() {
		super(ErrorCode.EXTERNAL_SERVICE_ERROR);
	}

	public CustomFeignException(String source) {
		super(ErrorCode.EXTERNAL_SERVICE_ERROR, source);
	}
}
