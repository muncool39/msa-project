package com.msa.order.application.client.dto.request;

import com.msa.order.domain.entity.Address;
import java.util.UUID;

public record CreateDeliveryRequest(
    UUID orderId,
    String receiverName,
    String receiverSlackId,
    Address address,
    UUID receiverCompanyId,
    UUID departureHubId,
	UUID destinationHubId
) {

}