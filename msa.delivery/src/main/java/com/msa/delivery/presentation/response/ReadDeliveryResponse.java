package com.msa.delivery.presentation.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.msa.delivery.domain.entity.Address;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.DeliveryRouteHistory;
import com.msa.delivery.domain.entity.enums.DeliveryStatus;

public record ReadDeliveryResponse(
	UUID deliveryId,
	UUID orderId,
	DeliveryStatus status,
	UUID departureHubId,
	UUID destinationHubId,
	Address receiverAddress,
	String receiverName,
	String receiverSlackId,
	LocalDateTime deliveredAt,
	Long companyDeliverId,
	List<DeliveryRouteResponse> routeHistory
) {
	public record DeliveryRouteResponse(
		UUID waypointId,
		long sequence,
		Long hubDeliveryId,
		UUID departureHubId,
		UUID destinationHubId,
		DeliveryStatus status,
		double estimatedDistance,
		long estimatedTime,
		double actualDistance,
		long actualTime,
		LocalDateTime updatedAt
	) {
		public static DeliveryRouteResponse from(DeliveryRouteHistory waypoint) {
			return new DeliveryRouteResponse(
				waypoint.getId(),
				waypoint.getSequence(),
				waypoint.getDeliverId(),
				waypoint.getDepartureHubId(),
				waypoint.getDestinationHubId(),
				waypoint.getStatus(),
				waypoint.getEstimatedDistance(),
				waypoint.getEstimatedTime(),
				waypoint.getActualDistance(),
				waypoint.getActualTime(),
				waypoint.getUpdatedAt()
			);
		}
	}

	public static ReadDeliveryResponse from(Delivery delivery) {
		return new ReadDeliveryResponse(
			delivery.getId(),
			delivery.getOrderId(),
			delivery.getStatus(),
			delivery.getDepartureHubId(),
			delivery.getDestinationHubId(),
			delivery.getAddress(),
			delivery.getReceiverName(),
			delivery.getReceiverSlackId(),
			delivery.getDeliveredAt(),
			delivery.getDeliverId(),
			delivery.getDeliveryHistories().stream()
				.map(DeliveryRouteResponse::from)
				.toList()
		);
	}

}
