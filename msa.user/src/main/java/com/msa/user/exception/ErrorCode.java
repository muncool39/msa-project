package com.msa.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
