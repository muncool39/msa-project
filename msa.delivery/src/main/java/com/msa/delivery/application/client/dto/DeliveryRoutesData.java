package com.msa.delivery.application.client.dto;

import java.util.List;

public record DeliveryRoutesData(
	String sourceHubId,
	String destinationId,
	Double totalDistance,
	Long totalDuration,
	List<WaypointResponse> waypoints

) {
	public record WaypointResponse(
		String hubId,
		double distanceFromPrevious,
		int durationFromPrevious,
		int order
	) {
	}
}