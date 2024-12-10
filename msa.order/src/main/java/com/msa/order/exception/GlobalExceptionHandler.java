package com.msa.order.exception;

import com.msa.order.exception.BusinessException.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException e) {
    return ResponseEntity.status(e.getErrorCode().getHttpStatus())
        .body(new ExceptionResponse(e.getMessage()));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse(e.getMessage()));
  }
}

