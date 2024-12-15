package com.msa.notification.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없습니다."),

    ERROR_SENDING_MESSAGE(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 전송 중 오류가 발생했습니다."),
    NOT_ALLOWED_API(HttpStatus.FORBIDDEN, "허용되지 않은 API 요청입니다."),
    NOT_FOUND_SLACK_NOTIFICATION(HttpStatus.NOT_FOUND, "해당 슬랙 메시지를 찾을 수 없습니다."),


    ;


    private final HttpStatus httpStatus;
    private final String message;
}
