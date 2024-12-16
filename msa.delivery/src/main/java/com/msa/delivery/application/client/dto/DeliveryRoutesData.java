package com.msa.delivery.application.client.dto;

import java.util.List;

public record DeliveryRoutesData(
	String hubRouteId,
	String sourceHubId,
	String sourceHubName,
	String destinationHubId,
	String destinationHubName,
	Double totalDistance,
	Long totalDuration,
	List<WaypointResponse> waypoints

) {
	public record WaypointResponse(
		String waypointId,
		String hubId,
		String hubName,
		double distanceFromPrevious,
		int durationFromPrevious,
		int order
	) {
	}
}