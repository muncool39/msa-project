package com.msa.hub.application.dto;

import com.msa.hub.domain.model.Hub;
import java.time.LocalDateTime;

public record HubBasicResponse(
        String id,
        String name,
        String city,
        String district,
        String streetName,
        String streetNumber,
        String addressDetail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static HubBasicResponse fromEntity(Hub hub) {
        return new HubBasicResponse(
                hub.getId(),
                hub.getName(),
                hub.getCity(),
                hub.getDistrict(),
                hub.getStreetName(),
                hub.getStreetNumber(),
                hub.getAddressDetail(),
                hub.getCreatedAt(),
                hub.getUpdatedAt()
        );
    }
}
