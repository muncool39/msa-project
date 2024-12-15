package com.msa.delivery.presentation.response;

import java.util.UUID;

import com.msa.delivery.domain.entity.Delivery;

public record DeliveryDataResponse(
	UUID deliveryId,
	UUID orderId,
	String status,
	UUID departureHubId,
	UUID destinationHubId,
	Long companyDeliverId
) {
	public static DeliveryDataResponse from(Delivery delivery) {
		return new DeliveryDataResponse(
			delivery.getId(),
			delivery.getOrderId(),
			delivery.getStatus().name(),
			delivery.getDepartureHubId(),
			delivery.getDestinationHubId(),
			delivery.getDeliverId()
		);

	}

}
