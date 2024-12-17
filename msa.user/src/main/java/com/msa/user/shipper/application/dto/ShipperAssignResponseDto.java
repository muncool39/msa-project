package com.msa.user.shipper.application.dto;

import java.util.List;

public record ShipperAssignResponseDto(
        List<ShipperAssignDetailDto> paths,
        Long companyDeliverId
) {

}