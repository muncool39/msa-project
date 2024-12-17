package com.msa.user.shipper.application.dto;

public record ShipperAssignDetailDto(
        String nodeId,
        Long hubDeliverId,
        Long companyDeliverId
) {
}
