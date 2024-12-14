package com.msa.delivery.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다. "),

  ORDER_CHANGE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "이미 배송이 시작되어 주문 수정이 불가합니다. "),

  HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브 정보를 찾을 수 없습니다. "),
  DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 정보를 찾을 수 없습니다. "),

  EXTERNAL_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서비스 호출에 문제가 발생했습니다. ");


  private final HttpStatus httpStatus;
  private final String message;

}
