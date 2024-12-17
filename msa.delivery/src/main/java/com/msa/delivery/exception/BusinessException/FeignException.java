package com.msa.delivery.exception.BusinessException;

import com.msa.delivery.exception.ErrorCode;

public class FeignException extends BusinessException{

	public FeignException(ErrorCode errorCode) {
		super(errorCode);
	}
}
