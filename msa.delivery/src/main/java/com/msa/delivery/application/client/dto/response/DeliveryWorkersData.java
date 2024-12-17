package com.msa.delivery.application.client.dto.response;

import java.util.List;

public record DeliveryWorkersData(
	List<DeliveryPathNode> paths,
	Long companyDeliverId
) {
	public record DeliveryPathNode(
		String nodeId,
		Long hubDeliverId,
		Long companyDeliverId
	) {
	}
}