package com.msa.hub.application.dto;

import com.msa.hub.domain.model.Waypoint;

public record WayPointResponse(
        String waypointId,
        String hubId,
        String hubName,
        double distanceFromPrevious,
        int durationFromPrevious,
        int order
) {
    public static WayPointResponse fromEntity(Waypoint waypoint) {
        return new WayPointResponse(
                waypoint.getId(),
                waypoint.getHub().getId(),
                waypoint.getHub().getName(),
                waypoint.getDistanceFromPrevious(),
                waypoint.getDurationFromPrevious(),
                waypoint.getSequence()
        );
    }
}
