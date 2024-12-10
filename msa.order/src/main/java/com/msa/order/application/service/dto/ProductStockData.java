package com.msa.order.application.service.dto;

import java.util.UUID;

public record ProductStockData(
    UUID id,
    UUID hubId
) {

}