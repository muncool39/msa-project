package com.msa.notification.exception.businessException;

import com.msa.notification.exception.ErrorCode;

public class NotificationApiException extends BusinessException {
    public NotificationApiException(ErrorCode errorCode) {
        super(errorCode);
    }
}
