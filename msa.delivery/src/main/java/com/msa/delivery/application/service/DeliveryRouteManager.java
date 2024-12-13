package com.msa.delivery.application.service;

import java.util.UUID;

import com.msa.delivery.application.dto.DeliveryRoutesData;

public interface DeliveryRouteManager {

	DeliveryRoutesData getDeliveryRoutes(UUID sourceHubId, UUID destinationHubId);
}
