package com.msa.company.presentation.response;

import com.msa.company.domain.entity.Company;
import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyListResponse(
        UUID id,
        String name,
        String city,
        UUID hubId,
        String type,
        String status,
        LocalDateTime createdAt
) {
    public static CompanyListResponse from(Company company) {
        return new CompanyListResponse(
                company.getId(),
                company.getName(),
                company.getAddress().getCity(),
                company.getHubId(),
                company.getType().name(),
                company.getStatus().name(),
                company.getCreatedAt()
        );
    }
}
