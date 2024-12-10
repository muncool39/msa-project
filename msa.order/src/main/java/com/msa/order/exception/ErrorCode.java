package com.msa.order.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

  STOCK_REDUCTION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "재고 감소 실패"),
  STOCK_RESTORE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "재고 복구 실패"),
  STOCK_NOT_ENOUGH(HttpStatus.BAD_REQUEST, "재고 부족"),

  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문을 찾을 수 없습니다"),
  ORDER_CREATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "주문 서비스 에러"),

  REQUEST_DELIVERY_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "배송 요청 실패");


  private final HttpStatus httpStatus;
  private final String message;

}
