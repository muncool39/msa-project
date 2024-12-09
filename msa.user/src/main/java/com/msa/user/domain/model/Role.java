package com.msa.user.domain.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {
    MASTER("마스터 관리자"),
    HUB_MANAGER("허브 관리자"),
    COMPANY_MANAGER("업체 담당자"),
    DELIVERY_MANAGER("배송 담당자");

    private final String description;
}
