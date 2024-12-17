package com.msa.delivery.infrastructure.client.fallback;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.delivery.application.client.dto.response.DeliveryRoutesData;
import com.msa.delivery.infrastructure.client.impl.DeliveryRouteClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Delivery-Route-Client")
@Component("deliveryRouteFallback")
public class DeliveryRouteClientFallback implements DeliveryRouteClient {

	@Override
	public DeliveryRoutesData getDeliveryRoutes(UUID sourceHubId, UUID destinationHubId) {
		log.error("허브 경로 조회 서비스 에러");
		return null;
	}

}
