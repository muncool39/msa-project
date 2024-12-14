package com.msa.delivery.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.delivery.application.dto.DeliveryRoutesData;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Delivery-Route-Client")
@Component("deliveryRouteFallback")
public class DeliveryRouteClientFallback implements DeliveryRouteClient {
	@Override
	public DeliveryRoutesData getDeliveryRoutes(UUID sourceHubId, UUID destinationHubId) {
		return null;
	}

}
