package com.msa.order.domain.entity;

import java.util.UUID;

import com.msa.order.domain.entity.enums.OrderStatus;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.ErrorCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(nullable = false)
	private UUID supplierCompanyId;

	@Column(nullable = false)
	private UUID receiverCompanyId;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(nullable = false)
	private UUID itemId;

	@Column(nullable = false)
	private String itemName;

	@Column(nullable = false)
	private Long quantity;

	private String description;

	private Long deliveryId;

	@Embedded
	private Address address;

	@Column(nullable = false)
	private UUID departureHubId;


	public static Order create(UUID supplierCompanyId, UUID receiverCompanyId, UUID itemId,
		String itemName, Long quantity, String description, String city, String district, String streetName,
		String streetNum, String detail, UUID departureHubId) {

		Address address = Address.of(city, district, streetName, streetNum, detail);

		return Order.builder()
			.supplierCompanyId(supplierCompanyId)
			.receiverCompanyId(receiverCompanyId)
			.status(OrderStatus.ORDER_REQUEST)
			.itemId(itemId)
			.itemName(itemName)
			.quantity(quantity)
			.description(description)
			.address(address)
			.departureHubId(departureHubId)
			.build();
	}

	public void updateDeliveryId(Long deliveryId) {
		this.status = OrderStatus.ORDERED;
		this.deliveryId = deliveryId;
	}

	public void updateItemInfo(UUID itemId, Long quantity) {
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public void cancelOrder(String canceledBy) {
		this.status = OrderStatus.ORDER_CANCELED;
		super.cancelOrder(canceledBy);
	}

	public void validateChangeOrder(String status) {
		final String HUB_WAITING = "HUB_WAITING";

		if (!HUB_WAITING.equals(status)) {
			throw new OrderException(ErrorCode.ORDER_CHANGE_NOT_ALLOWED);
		}
	}

	public void deleteOrder(String deletedBy) {
		super.deleteOrder(deletedBy);
	}

}
