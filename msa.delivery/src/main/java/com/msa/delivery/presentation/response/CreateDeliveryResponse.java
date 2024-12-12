package com.msa.delivery.presentation.response;

import java.util.UUID;

import com.msa.delivery.domain.entity.Delivery;

public record CreateDeliveryResponse(
    UUID deliveryId,
    UUID orderId,
    String status,
    String city,
    String district,
    String streetName,
    String streetNum,
    String detail,
    UUID departureHubId
) {

	public static CreateDeliveryResponse from(Delivery delivery) {
		return new CreateDeliveryResponse(
			delivery.getId(),
			delivery.getOrderId(),
			delivery.getStatus().name(),
			delivery.getAddress().getCity(),
			delivery.getAddress().getDistrict(),
			delivery.getAddress().getStreetName(),
			delivery.getAddress().getStreetNum(),
			delivery.getAddress().getDetail(),
			delivery.getDepartureHubId()
		);

	}
}
