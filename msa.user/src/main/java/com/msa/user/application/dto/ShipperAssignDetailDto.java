package com.msa.user.application.dto;

public record ShipperAssignDetailDto(
        String nodeId,
        Long hubDeliverId,
        Long companyDeliverId
) {
}
