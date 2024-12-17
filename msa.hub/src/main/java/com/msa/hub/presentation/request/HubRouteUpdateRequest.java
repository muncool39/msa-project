package com.msa.hub.presentation.request;

public record HubRouteUpdateRequest(
        Double totalDistance,
        Long totalDuration
) {
}
