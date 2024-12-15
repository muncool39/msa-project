package com.msa.company.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateProductRequest(

        @NotBlank(message = "상품명을 필수로 입력해주세요.")
        String name,

        @NotNull(message = "재고 수량을 필수로 입력해주세요.")
        @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
        Long stock
) {
}
