package com.msa.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
