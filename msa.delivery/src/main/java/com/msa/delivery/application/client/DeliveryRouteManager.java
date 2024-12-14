package com.msa.delivery.application.client;

import java.util.UUID;

import com.msa.delivery.application.client.dto.DeliveryRoutesData;

public interface DeliveryRouteManager {

	DeliveryRoutesData getDeliveryRoutes(UUID sourceHubId, UUID destinationHubId);
}
