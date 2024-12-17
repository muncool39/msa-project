package com.msa.delivery.application.client.dto.request;

import java.util.List;
import java.util.UUID;

public record GetDeliveryWorkersRequest(
	List<DeliveryNode> path
) {

	public record DeliveryNode(
		UUID nodeId,
		long sequence,
		UUID departureHubId,
		UUID destinationHubId
	) {

	}
}