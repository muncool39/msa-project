package com.msa.order.application.service.dto;

import com.msa.order.domain.entity.Address;
import java.util.UUID;

public record CreateDeliveryRequest(
    UUID orderId,
    UUID receiverCompanyId,
    String receiverName,
    String receiverSlackId,
    Address address,
    UUID supplierCompanyId,
    UUID departureHubId
) {

}