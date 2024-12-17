package com.msa.user.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNSUPPORTED_SORT_TYPE(HttpStatus.BAD_REQUEST, "지원하지 않는 정렬 방식입니다."),
    INVALID_HUB(HttpStatus.BAD_REQUEST, "유효하지 않은 허브입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 사용자 정보가 존재하지 않습니다."),
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 사용자입니다."),

    //shipper
    SHIPPER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 배송원 정보가 존재하지 않습니다."),
    INVALID_HUB_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 허브 ID입니다."),
    NO_AVAILABLE_HUB_SHIPPERS(HttpStatus.NOT_FOUND, "이용 가능한 배송원이 존재하지 않습니다."),
    INVALID_COMPANY_SHIPPER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 업체 배송 담당자 ID입니다."),
    COMPANY_SHIPPER_NOT_FOUND(HttpStatus.NOT_FOUND, "일치하는 업체 배송 담당자 정보가 존재하지 않습니다."),
    COMPANY_SHIPPER_HUB_MISMATCH(HttpStatus.BAD_REQUEST, "업체 배송 담당자와 허브가 일치하지 않습니다."),
    COMPANY_SHIPPER_NOT_AVAILABLE(HttpStatus.BAD_REQUEST, "이용 가능한 업체 배송 담당자가 존재하지 않습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "유효하지 않은 요청입니다."),
    INSUFFICIENT_DELIVERY_MANAGERS(HttpStatus.NOT_FOUND, "배송 담당자가 부족합니다.")

    ;
    private final HttpStatus httpStatus;
    private final String message;
    }
