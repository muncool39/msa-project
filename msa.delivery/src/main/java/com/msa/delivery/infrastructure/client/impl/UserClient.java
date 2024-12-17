package com.msa.delivery.infrastructure.client.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.request.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.response.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.response.ShipperData;
import com.msa.delivery.application.client.dto.response.UserData;
import com.msa.delivery.infrastructure.client.fallback.UserClientFallbackFactory;
import com.msa.delivery.infrastructure.config.feign.FeignConfiguration;
import com.msa.delivery.presentation.response.ApiResponse;

@FeignClient(
	name = "user-service",
	qualifiers = "userClient",
	configuration = FeignConfiguration.class,
	fallbackFactory = UserClientFallbackFactory.class
)
public interface UserClient extends UserManager {

	@GetMapping("/users/{userId}")
	ApiResponse<UserData> getUserInfo(@PathVariable(name = "userId") Long userId);

	@PostMapping("/shippers/assign")
	ApiResponse<DeliveryWorkersData> assignDeliveryWorkers(GetDeliveryWorkersRequest request);

	@GetMapping("/shippers/{shipperId}")
	ApiResponse<ShipperData> getShipperInfo(@PathVariable(name = "shipperId") Long deliverId);
}
