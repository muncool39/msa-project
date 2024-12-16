package com.msa.user.shipper.application.dto;

import java.util.List;

public record ShipperAssignResponseDto(
        List<AssignedPathDto> paths,
        String companyDeliverId
) {
    public static record AssignedPathDto(
            String nodeId,
            String hubDeliverId,
            String companyDeliverId  // 업체 배송 담당자 ID

    ) {}

}