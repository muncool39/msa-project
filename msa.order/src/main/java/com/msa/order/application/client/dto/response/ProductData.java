package com.msa.order.application.client.dto.response;

import java.util.UUID;

public record ProductData(
    UUID productId,
	UUID companyId,
	UUID hubId,
	Long updateStock
) {

}