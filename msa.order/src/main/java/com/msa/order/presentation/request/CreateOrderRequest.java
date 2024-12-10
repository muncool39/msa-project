package com.msa.order.presentation.request;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateOrderRequest(

    @NotNull
    UUID supplierCompanyId,

    @NotNull
    UUID itemId,

    @NotNull
    int quantity,

    String description,

    @NotNull
    String receiverName,

    @NotNull
    String city,

    @NotNull
    String district,

    @NotNull
    String streetName,

    @NotNull
    String streetNum,

    @NotNull
    String detail) {

}
