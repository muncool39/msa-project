package com.msa.order.application.service.dto;

import java.util.UUID;

public record ProductStockResponse(
    UUID id,
    UUID hubId
) {

}