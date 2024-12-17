package com.msa.delivery.application.client.dto.response;

import java.util.UUID;

public record ShipperData(
	UUID id,
	Long userId,
	String hubId,
	String type,
	Integer deliveryOrder
) {
}
