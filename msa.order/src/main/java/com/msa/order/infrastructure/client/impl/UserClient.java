package com.msa.order.infrastructure.client.impl;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msa.order.application.client.UserManager;
import com.msa.order.application.client.dto.response.UserData;
import com.msa.order.infrastructure.config.feign.FeignConfiguration;
import com.msa.order.infrastructure.client.fallback.UserClientFallback;
import com.msa.order.presentation.response.ApiResponse;

@FeignClient(
	name = "user-service",
	fallback = UserClientFallback.class,
	qualifiers = "userClient",
	configuration = FeignConfiguration.class)
public interface UserClient extends UserManager {

	@GetMapping("/users/{id}")
	ApiResponse<UserData> getUserInfo(@PathVariable(name = "id") Long userId);

}
