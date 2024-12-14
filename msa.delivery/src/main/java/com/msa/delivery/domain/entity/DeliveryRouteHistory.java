package com.msa.delivery.domain.entity;

import java.util.UUID;

import com.msa.delivery.domain.entity.enums.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery_route_history")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class DeliveryRouteHistory extends BaseEntity {

	@Id
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;

	@Column(nullable = false)
	private long sequence;

	private Long deliverId; // hub 배송 담당자

	@Column(nullable = false)
	private UUID departureHubId;

	@Column(nullable = false)
	private UUID destinationHubId;

	private double estimatedDistance; // km

	private long estimatedTime; // min

	private double actualDistance; // km

	private long actualTime; // min

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus status;


	public static DeliveryRouteHistory create(long sequence, Long deliverId, UUID departureHubId,
		UUID destinationHubId, double estimatedDistance, long estimatedTime) {

		return DeliveryRouteHistory.builder()
			.id(UUID.randomUUID())
			.sequence(sequence)
			.deliverId(deliverId)
			.departureHubId(departureHubId)
			.destinationHubId(destinationHubId)
			.estimatedDistance(estimatedDistance)
			.estimatedTime(estimatedTime)
			.status(DeliveryStatus.HUB_WAITING)
			.build();
	}

	protected void setDelivery(Delivery delivery) {
		this.delivery = delivery;
	}

	public void updateActualDeliveryInfo(double actualDistance, long actualTime) {
		this.actualDistance = actualDistance;
		this.actualTime = actualTime;
		this.status = DeliveryStatus.HUB_ARRIVED;
	}

	public void assignDeliveryWorker(Long deliverId) {
		this.deliverId = deliverId;
	}

}