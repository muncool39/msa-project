package com.msa.order.application.client.dto;

import java.util.UUID;

public record ProductStockData(
    UUID id,
    UUID hubId
) {

}