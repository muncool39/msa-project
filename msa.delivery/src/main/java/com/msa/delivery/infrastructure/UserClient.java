package com.msa.delivery.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.UserData;
import com.msa.delivery.config.FeignConfiguration;
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
