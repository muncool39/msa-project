package com.msa.order.application.client.dto;

import com.msa.order.domain.entity.Address;
import java.util.UUID;

public record CreateDeliveryRequest(
    UUID orderId,
    String receiverName,
    String receiverSlackId,
    Address address,
    UUID supplierCompanyId,
    UUID departureHubId,
	UUID destinationHubId
) {

}