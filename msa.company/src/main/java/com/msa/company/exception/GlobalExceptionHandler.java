package com.msa.company.exception;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<ErrorResponse> handleCompanyException(CompanyException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    // Feign 예외 처리
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorResponse> handleFeignException(FeignException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
