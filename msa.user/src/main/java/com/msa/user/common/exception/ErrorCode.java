package com.msa.user.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_HUB(HttpStatus.BAD_REQUEST, "유효하지 않은 허브입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 사용자 정보가 존재하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
