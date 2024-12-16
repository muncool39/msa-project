package com.msa.notification.exception.businessException;

import com.msa.notification.exception.ErrorCode;

public class AiRequestApiException extends BusinessException {
    public AiRequestApiException(ErrorCode errorCode) {
        super(errorCode);
    }
}
