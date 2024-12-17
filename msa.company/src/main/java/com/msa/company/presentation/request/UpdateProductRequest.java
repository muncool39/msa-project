package com.msa.company.presentation.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record UpdateProductRequest(

        String name,

        @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
        Long stock
) {
}
