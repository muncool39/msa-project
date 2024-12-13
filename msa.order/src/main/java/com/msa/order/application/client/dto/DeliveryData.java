package com.msa.order.application.client.dto;

import java.util.UUID;

public record DeliveryData(
    Long deliveryId,
    UUID orderId,
    String status,
	UUID departureHubId,
	UUID destinationHubId,
	Long companyDeliverId
) {

}