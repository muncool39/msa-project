package com.msa.company.presentation.response;

import com.msa.company.domain.entity.Product;
import java.util.UUID;

public record StockResponse(
        UUID productId,
        UUID companyId,
        UUID hubId,
        Long updatedStock
) {
    public static StockResponse from(Product product) {
        return new StockResponse(
                product.getId(),
                product.getCompany().getId(),
                product.getCompany().getHubId(),
                product.getStock()
        );
    }
}
