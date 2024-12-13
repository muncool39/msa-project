package com.msa.hub.application.dto;

import com.msa.hub.domain.model.HubRoute;
import com.msa.hub.domain.model.Waypoint;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record HubRouteDetailResponse(
        String hubRouteId,
        String sourceHubId,
        String sourceHubName,
        String destinationHubId,
        String destinationHubName,
        Double totalDistance,
        Long totalDuration,
        List<WayPointResponse> waypoints,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    public static HubRouteDetailResponse convertToResponse(HubRoute hubRoute) {
        return new HubRouteDetailResponse(
                hubRoute.getId(),
                hubRoute.getSourceHub().getId(),
                hubRoute.getSourceHub().getName(),
                hubRoute.getDestinationHub().getId(),
                hubRoute.getDestinationHub().getName(),
                hubRoute.getTotalDistance(),
                hubRoute.getTotalDuration(),
                convert(hubRoute.getWaypoints()),
                hubRoute.getCreatedAt(),
                hubRoute.getUpdatedAt()
        );
    }

    private static List<WayPointResponse> convert(List<Waypoint> waypoints) {
        return Optional.ofNullable(waypoints)
                .orElse(List.of())
                .stream()
                .sorted(Comparator.comparingInt(Waypoint::getSequence))
                .map(WayPointResponse::fromEntity)
                .toList();
    }
}
