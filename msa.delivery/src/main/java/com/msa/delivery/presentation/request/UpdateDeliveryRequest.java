package com.msa.delivery.presentation.request;

import java.util.UUID;

public record UpdateDeliveryRequest(

	UUID routeId,

	Double actualDistance,

	long actualTime
) {
}
