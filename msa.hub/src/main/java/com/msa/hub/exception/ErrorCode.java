package com.msa.hub.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_ROLE(HttpStatus.UNAUTHORIZED, "허브 관리자 권한이 존재하지 않습니다."),
    DUPLICATE_HUB_NAME(HttpStatus.BAD_REQUEST, "이미 존재하는 허브입니다."),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 허브 정보를 찾을 수 없습니다.");
    private final HttpStatus httpStatus;
    private final String message;
}
