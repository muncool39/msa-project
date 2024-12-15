package com.msa.company.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record ProductRequest(

        @NotNull(message = "업체 ID는 필수입니다.")
        UUID companyId,

        @NotBlank(message = "상품명은 필수입니다.")
        String name,

        @NotNull(message = "재고 수량은 필수입니다.")
        @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
        Long stock
) {
}
