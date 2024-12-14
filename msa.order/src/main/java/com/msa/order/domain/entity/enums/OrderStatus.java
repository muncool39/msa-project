package com.msa.order.domain.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum OrderStatus {

	ORDER_REQUEST("주문 요청"),
	ORDERED("주문 완료"),
	ORDER_CANCELED("주문 취소");

	private final String description;

}