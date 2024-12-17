package com.msa.company.application.dto.response;

import com.msa.company.domain.model.Product;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductListResponse(
        UUID id,
        String name,
        Long stock,
        UUID companyId,
        String companyName,
        Boolean isOutOfStock,
        LocalDateTime createdAt
) {
    public static ProductListResponse from(Product product) {
        return new ProductListResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getCompany().getId(),
                product.getCompany().getName(),
                product.getIsOutOfStock(),
                product.getCreatedAt()
        );
    }
}
