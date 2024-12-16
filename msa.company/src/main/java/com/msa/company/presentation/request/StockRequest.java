package com.msa.company.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record StockRequest(

        @NotNull(message = "수량을 입력해주세요.")
        @Min(value = 0, message = "수량은 0 이상이어야 합니다.")
        Long stock
) {
    public Long getStock() {
        return stock;
    }
}
