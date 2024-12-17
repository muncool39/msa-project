package com.msa.delivery.application.client.dto.request;

import java.util.List;

public record GetDeliveryWorkersRequest(
	List<DeliveryNode> paths
) {

	public record DeliveryNode(
		String nodeId,
		long sequence,
		String departureHubId,
		String destinationHubId
	) {

	}
}