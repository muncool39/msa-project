package com.msa.company.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "업체를 찾을 수 없습니다."),
    DELETED_COMPANY(HttpStatus.NOT_FOUND, "삭제된 업체입니다."),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    DUPLICATE_BUSINESS_NUMBER(HttpStatus.CONFLICT, "이미 등록된 사업자 번호입니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "상품의 재고가 부족합니다."),
    DELETED_PRODUCT(HttpStatus.NOT_FOUND, "삭제된 상품입니다."),
    EXTERNAL_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서비스 호출에 문제가 발생했습니다."),
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브를 찾을 수 없습니다."),
    PRODUCT_NOT_FOUND_IN_COMPANY(HttpStatus.NOT_FOUND, "이 업체에는 상품이 존재하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
