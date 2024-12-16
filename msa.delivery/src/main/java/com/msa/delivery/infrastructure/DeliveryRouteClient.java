package com.msa.delivery.infrastructure;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msa.delivery.application.client.DeliveryRouteManager;
import com.msa.delivery.application.client.dto.DeliveryRoutesData;
import com.msa.delivery.config.FeignConfiguration;

@FeignClient(
	name = "hub-service",
	fallback = DeliveryRouteClientFallback.class,
	qualifiers = "deliveryRouteClient",
	configuration = FeignConfiguration.class)
public interface DeliveryRouteClient extends DeliveryRouteManager {

	@GetMapping("/hub-routes/delivery")
	DeliveryRoutesData getDeliveryRoutes(
		@RequestParam("sourceHubId") UUID sourceHubId,
		@RequestParam("destinationHubId") UUID destinationHubId);

}
