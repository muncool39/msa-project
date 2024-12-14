package com.msa.delivery.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.msa.delivery.domain.entity.enums.DeliveryStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_delivery")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID orderId;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private DeliveryStatus status;

	@Column(nullable = false)
	private UUID departureHubId;

	@Column(nullable = false)
	private UUID destinationHubId;

	@Embedded
	private Address address;

	@Column(nullable = false)
	private String receiverName;

	@Column(nullable = false)
	private String receiverSlackId;

	private Long deliverId; // 업체 배송 담당자

	private LocalDateTime deliveredAt;

	private Long deliveredBy;

	@OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY)
	@Builder.Default
	private List<DeliveryRouteHistory> deliveryHistories = new ArrayList<>();


	public static Delivery create(UUID orderId, UUID departureHubId, UUID destinationHubId, Address address,
		String receiverName, String receiverSlackId) {

		return Delivery.builder()
			.orderId(orderId)
			.status(DeliveryStatus.HUB_WAITING)
			.departureHubId(departureHubId)
			.destinationHubId(destinationHubId)
			.address(address)
			.receiverName(receiverName)
			.receiverSlackId(receiverSlackId)
			.build();

	}

	public void addDeliveryHistories(List<DeliveryRouteHistory> histories) {
		histories.forEach(this::addDeliveryRouteHistory);
	}

	public void addDeliveryRouteHistory(DeliveryRouteHistory history) {
		this.deliveryHistories.add(history);
		history.setDelivery(this);
	}

	public void assignCompanyDeliveryWorker(Long deliverId) {
		this.deliverId = deliverId;
	}




}