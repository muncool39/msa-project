package com.msa.company.domain.model.enums;

import lombok.Getter;

@Getter
public enum CompanyType {
    SUPPLIER("생산 업체"),
    RECEIVER("수령 업체");

    private final String description;

    CompanyType(String description) {
        this.description = description;
    }
}
