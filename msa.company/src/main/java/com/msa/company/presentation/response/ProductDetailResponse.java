package com.msa.company.presentation.response;

import com.msa.company.domain.entity.Product;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDetailResponse(
        UUID id,
        String name,
        Long stock,
        Boolean isOutOfStock,
        UUID companyId,
        String companyName,
        UUID hubId,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updateAt,
        Long updateBy
) {
    public static ProductDetailResponse from(Product product) {
        return new ProductDetailResponse(
                product.getId(),
                product.getName(),
                product.getStock(),
                product.getIsOutOfStock(),
                product.getCompany().getId(),
                product.getCompany().getName(),
                product.getCompany().getHubId(),
                product.getCreatedAt(),
                product.getCreatedBy(),
                product.getUpdatedAt(),
                product.getUpdatedBy()
        );
    }
}
