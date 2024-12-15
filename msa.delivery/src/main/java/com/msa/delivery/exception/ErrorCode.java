package com.msa.delivery.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다. "),
  NOT_ALLOWED_HUB_MANAGER(HttpStatus.UNAUTHORIZED, "[허브 관리자] 담당 허브의 배송 내역만 처리 가능합니다. "),
  NOT_ALLOWED_COMPANY_MANAGER(HttpStatus.UNAUTHORIZED, "[업체 관리자] 담당 업체의 배송 내역만 처리 가능합니다. "),
  NOT_ALLOWED_HUB_DELIVER(HttpStatus.UNAUTHORIZED, "[허브 배송담당자] 담당 허브의 배송 내역만 처리 가능합니다. "),
  NOT_ALLOWED_COMPANY_DELIVER(HttpStatus.UNAUTHORIZED, "[업체 배송담당자] 담당 업체의 배송 내역만 처리 가능합니다. "),

  ORDER_CHANGE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "이미 배송이 시작되어 주문 수정이 불가합니다. "),

  HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브 정보를 찾을 수 없습니다. "),
  DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 정보를 찾을 수 없습니다. "),
  DELIVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 경로를 찾을 수 없습니다. "),

  USER_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "유저 서비스 호출에 문제가 발생했습니다. "),
  EXTERNAL_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서비스 호출에 문제가 발생했습니다. ");


  private final HttpStatus httpStatus;
  private final String message;

}
