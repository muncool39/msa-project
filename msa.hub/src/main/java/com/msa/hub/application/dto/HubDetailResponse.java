package com.msa.hub.application.dto;

import com.msa.hub.domain.model.Hub;
import java.time.LocalDateTime;

public record HubDetailResponse(
        String id,
        String name,
        String city,
        String district,
        String streetName,
        String streetNumber,
        String addressDetail,
        Double latitude,
        Double longitude,
        Long managerId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static HubDetailResponse from(Hub hub) {
        return new HubDetailResponse(
                hub.getId(),
                hub.getName(),
                hub.getCity(),
                hub.getDistrict(),
                hub.getStreetName(),
                hub.getStreetNumber(),
                hub.getAddressDetail(),
                hub.getLatitude(),
                hub.getLongitude(),
                hub.getManagerId(),
                hub.getCreatedAt(),
                hub.getUpdatedAt()
        );
    }
}
