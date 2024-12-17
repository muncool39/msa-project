package com.msa.user.application.dto;

import java.util.List;

public record ShipperAssignResponseDto(
        List<ShipperAssignDetailDto> paths,
        Long companyDeliverId
) {

}