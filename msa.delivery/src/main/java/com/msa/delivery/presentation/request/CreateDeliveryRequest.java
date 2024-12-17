package com.msa.delivery.presentation.request;

import java.util.UUID;

import com.msa.delivery.domain.entity.Address;

import jakarta.validation.constraints.NotNull;

public record CreateDeliveryRequest(

	@NotNull
	UUID orderId,

	@NotNull
	UUID receiveCompanyId,

    @NotNull
    UUID departureHubId,

    @NotNull
    UUID destinationHubId,

	@NotNull
	Address address,

	@NotNull
	String receiverName,

	@NotNull
	String receiverSlackId) {

}
