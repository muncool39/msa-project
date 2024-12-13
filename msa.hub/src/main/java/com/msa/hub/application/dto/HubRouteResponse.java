package com.msa.hub.application.dto;

import com.msa.hub.domain.model.HubRoute;
import java.time.LocalDateTime;

public record HubRouteResponse(
        String hubRouteId,
        String sourceHubId,
        String sourceHubName,
        String destinationHubId,
        String destinationHubName,
        Double totalDistance,
        Long totalDuration,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static HubRouteResponse convertToResponse(HubRoute hubRoute) {
        return new HubRouteResponse(
                hubRoute.getId(),
                hubRoute.getSourceHub().getId(),
                hubRoute.getSourceHub().getName(),
                hubRoute.getDestinationHub().getId(),
                hubRoute.getDestinationHub().getName(),
                hubRoute.getTotalDistance(),
                hubRoute.getTotalDuration(),
                hubRoute.getCreatedAt(),
                hubRoute.getUpdatedAt()
        );
    }
}
