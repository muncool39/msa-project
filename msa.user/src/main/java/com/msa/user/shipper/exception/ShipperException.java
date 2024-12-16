package com.msa.user.shipper.exception;

import com.msa.user.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ShipperException extends RuntimeException {

    private final HttpStatus httpStatus;
    private final String message;

    public ShipperException(ErrorCode errorCode) {
        this.httpStatus = errorCode.getHttpStatus();
        this.message = errorCode.getMessage();
    }
}
