package com.msa.order.application.client.dto;

import java.util.UUID;

public record DeliveryData(
    Long deliveryId,
    UUID orderId,
    String status,
    String city,
    String district,
    String streetName,
    String streetNum,
    String detail,
	UUID departureHubId
) {


}