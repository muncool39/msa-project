package com.msa.notification.exception.businessException;

import com.msa.notification.exception.ErrorCode;

public class UnauthorizedException extends BusinessException {
    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
