package com.msa.order.presentation.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CreateOrderRequest(

	@NotNull
	UUID itemId,

	@NotNull
	String itemName,

	@NotNull
	int quantity,

	String description,

	@NotNull
	UUID receiveCompanyId,

	@NotNull
	String receiverName,

	@NotNull
	String city,

	@NotNull
	String district,

	@NotNull
	String streetName,

	@NotNull
	String streetNum,

	@NotNull
	String detail) {

}
