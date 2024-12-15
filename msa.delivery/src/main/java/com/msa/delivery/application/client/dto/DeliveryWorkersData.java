package com.msa.delivery.application.client.dto;

import java.util.List;
import java.util.UUID;

public record DeliveryWorkersData(
	List<DeliveryPathNode> path,
	Long companyDeliveryId
) {
	public record DeliveryPathNode(
		UUID nodeId,
		Long deliveryId
	) {
	}
}