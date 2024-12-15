package com.msa.delivery.presentation.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

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
	LocalDateTime createdAt,
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
		LocalDateTime updatedAt,
		LocalDateTime createdAt
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
				waypoint.getUpdatedAt(),
				waypoint.getCreatedAt()
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
			delivery.getCreatedAt(),
			delivery.getDeliveryHistories().stream()
				.map(DeliveryRouteResponse::from)
				.toList()
		);
	}

	public static PageResponse<ReadDeliveryResponse> pageOf(Page<Delivery> pagingData) {
		Page<ReadDeliveryResponse> responses = pagingData.map(ReadDeliveryResponse::from);
		return PageResponse.of(responses);
	}
}
