package com.msa.user.shipper.application.dto;

import java.util.List;
import java.util.UUID;

public record ShipperAssignResponseDto(
        List<ShipperAssignDetailDto> paths,
        UUID companyDeliverId
) {

}