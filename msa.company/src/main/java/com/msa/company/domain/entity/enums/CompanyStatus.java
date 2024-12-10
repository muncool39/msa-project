package com.msa.company.domain.entity.enums;

import lombok.Getter;

@Getter
public enum CompanyStatus {
    PENDING("대기 중"),
    APPROVED("승인"),
    REJECTED("거부"),;

    private final String description;

    CompanyStatus(String description) {
        this.description = description;
    }
}
