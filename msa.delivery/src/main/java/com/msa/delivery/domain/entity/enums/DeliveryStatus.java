package com.msa.delivery.domain.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DeliveryStatus {
	DELIVERY_PENDING("배송경로 생성 대기"), // 최초
	HUB_WAITING("허브 대기 중"), // 현재 외
	HUB_MOVING("허브 이동 중"), // 현재
	HUB_ARRIVED("허브 도착"), // 지나간것들은 다 허브 도착
	DELIVERING("업체 배송 중"),
	DELIVERED("배송완료");

	private final String description;

}
