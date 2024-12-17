package com.msa.company.presentation.response;

import com.msa.company.domain.entity.Product;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductListResponse(
        UUID id,
        String name,
        Long stock,
        String companyName,
        Boolean isOutOfStock,
        LocalDateTime createdAt
) {
    public static ProductListResponse from(Product product) {
        return new ProductListResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getCompany().getName(),
                product.getIsOutOfStock(),
                product.getCreatedAt()
        );
    }
}
