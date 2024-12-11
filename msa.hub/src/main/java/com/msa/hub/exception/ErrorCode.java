package com.msa.hub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    DUPLICATE_HUB_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 허브입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
