package com.msa.user.shipper.application.dto;

import java.util.UUID;

public record ShipperAssignDetailDto(
        String nodeId,
        UUID hubDeliverId,
        UUID companyDeliverId
) {
}
