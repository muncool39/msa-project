package com.msa.order.application.client.dto.response;

import java.util.UUID;

public record CompanyData(
    UUID id,
	Long userId,
    String name,
	String businessNumber,
    UUID hubId,
	String type
) {

}
