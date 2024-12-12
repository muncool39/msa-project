package com.msa.user.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    private final String ERROR_LOG = "[ERROR] %s %s";

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ExceptionMessage> applicationException(final UserException e){
        log.error(String.format(ERROR_LOG, e.getHttpStatus(), e.getMessage()));
        return ResponseEntity.status(e.getHttpStatus()).body(new ExceptionMessage(e.getMessage()));
    }
}
