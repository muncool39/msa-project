package com.msa.company.application.dto.response;

import com.msa.company.domain.model.Address;
import com.msa.company.domain.model.Company;
import java.time.LocalDateTime;
import java.util.UUID;

public record CompanyListResponse(
        UUID id,
        String name,
        Address address,
        UUID hubId,
        String type,
        String status,
        LocalDateTime createdAt
) {
    public static CompanyListResponse from(Company company) {
        return new CompanyListResponse(
                company.getId(),
                company.getName(),
                company.getAddress(),
                company.getHubId(),
                company.getType().name(),
                company.getStatus().name(),
                company.getCreatedAt()
        );
    }
}
