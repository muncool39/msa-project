package com.msa.order.application.client.dto;

import java.util.UUID;

public record DeliveryData(
	UUID deliveryId,
    UUID orderId,
    String status,
	UUID departureHubId,
	UUID destinationHubId,
	Long companyDeliverId
) {

}