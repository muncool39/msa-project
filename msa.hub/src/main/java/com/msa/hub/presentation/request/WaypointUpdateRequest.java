package com.msa.hub.presentation.request;

public record WaypointUpdateRequest(
        Double distanceFromPrevious,
        Integer durationFromPrevious
) {
}
