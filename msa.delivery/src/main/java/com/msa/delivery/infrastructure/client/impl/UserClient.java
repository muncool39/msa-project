package com.msa.delivery.infrastructure.client.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.response.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.request.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.response.UserData;
import com.msa.delivery.infrastructure.config.feign.FeignConfiguration;
import com.msa.delivery.infrastructure.client.fallback.UserClientFallback;
import com.msa.delivery.presentation.response.ApiResponse;

@FeignClient(
	name = "user-service",
	qualifiers = "userClient",
	configuration = FeignConfiguration.class,
	fallback = UserClientFallback.class
)
public interface UserClient extends UserManager {

	@GetMapping("/users/{userId}")
	ApiResponse<UserData> getUserInfo(@PathVariable(name = "userId") Long userId);

	@GetMapping("/users") // TODO URL 설정 필요
	DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request);
}
