package com.msa.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다. "),

  STOCK_REDUCTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "재고 감소에 실패했습니다. "),
  STOCK_RESTORE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "재고 복구에 실패했습니다. "),
  STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "재고가 부족합니다. "),

  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다"),
  ORDER_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "주문 서비스에서 에러가 발생했습니다. "),

  REQUEST_DELIVERY_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "배송 요청이 실패했습니다. "),

  ORDER_CHANGE_NOT_ALLOWED(HttpStatus.FORBIDDEN, "이미 배송이 시작되어 주문 수정이 불가합니다. "),

  HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브 정보를 찾을 수 없습니다. "),
  DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 정보를 찾을 수 없습니다. ");


  private final HttpStatus httpStatus;
  private final String message;

}
