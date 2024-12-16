package com.msa.company.presentation.response;

import com.msa.company.domain.entity.Address;
import com.msa.company.domain.entity.Company;
import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyDetailResponse(
        UUID id,
        Long userId,
        String name,
        String businessNumber,
        Address address,
        UUID hubId,
        String type,
        String status,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updateAt,
        Long updateBy
) {
    public static CompanyDetailResponse from(Company company) {
        return new CompanyDetailResponse(
                company.getId(),
                company.getUserId(),
                company.getName(),
                company.getBusinessNumber(),
                company.getAddress(),
                company.getHubId(),
                company.getType().name(),
                company.getStatus().name(),
                company.getCreatedAt(),
                company.getCreatedBy(),
                company.getUpdatedAt(),
                company.getUpdatedBy()
        );
    }
}
