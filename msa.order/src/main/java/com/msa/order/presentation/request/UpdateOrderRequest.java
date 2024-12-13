package com.msa.order.presentation.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UpdateOrderRequest(

    @NotNull
    UUID supplierCompanyId,

    @NotNull
    UUID itemId,

    @NotNull
    String itemName,

    @NotNull @Min(1)
    int quantity) {

}