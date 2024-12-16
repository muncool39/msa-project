package com.msa.company.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // 권한 관련 에러
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
    INVALID_USER_ROLE(HttpStatus.FORBIDDEN, "사용자 역할이 일치하지 않습니다."),
    COMPANY_MANAGER_MISMATCH(HttpStatus.FORBIDDEN, "업체 관리자만 선택 가능합니다."),

    // 업체 관련 에러
    DUPLICATE_BUSINESS_NUMBER(HttpStatus.CONFLICT, "이미 등록된 사업자 번호입니다."),
    COMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "업체를 찾을 수 없습니다."),
    DELETED_COMPANY(HttpStatus.NOT_FOUND, "삭제된 업체입니다."),

    // 상품 관련 에러
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
    PRODUCT_OUT_OF_STOCK(HttpStatus.BAD_REQUEST, "상품의 재고가 부족합니다."),
    DELETED_PRODUCT(HttpStatus.NOT_FOUND, "삭제된 상품입니다."),
    PRODUCT_NOT_FOUND_IN_COMPANY(HttpStatus.NOT_FOUND, "이 업체에는 상품이 존재하지 않습니다."),

    // 허브 관련 에러
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "허브를 찾을 수 없습니다."),
    HUB_ID_REQUIRED(HttpStatus.BAD_REQUEST, "허브 ID는 필수 입력 값입니다."),
    HUB_ID_MISMATCH(HttpStatus.BAD_REQUEST, "허브 ID가 일치하지 않습니다."),

    // 사용자 관련 에러
    USER_NOT_ASSIGNED_TO_HUB(HttpStatus.NOT_FOUND, "소속된 허브가 없습니다."),
    USER_NOT_ASSIGNED_TO_COMPANY(HttpStatus.NOT_FOUND, "소속된 업체가 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자가 존재하지 않습니다."),
    UNAUTHORIZED_ACCESS_HUB(HttpStatus.BAD_REQUEST, "본인 허브에 소속된 업체만 수정할 수 있습니다."),
    UNAUTHORIZED_ACCESS_COMPANY(HttpStatus.BAD_REQUEST, "본인이 담당하는 업체만 수정할 수 있습니다."),

    // 기타 에러
    EXTERNAL_SERVICE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "외부 서비스 호출에 문제가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
