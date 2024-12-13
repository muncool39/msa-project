package com.msa.order.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msa.order.application.client.UserManager;
import com.msa.order.application.client.dto.UserData;
import com.msa.order.config.FeignConfiguration;

@FeignClient(
	name = "user-service",
	fallback = UserClientFallback.class,
	qualifiers = "userClient",
	configuration = FeignConfiguration.class)
public interface UserClient extends UserManager {

	@GetMapping("/users/{id}")
	UserData getUserInfo(@PathVariable(name = "id") Long userId);

}
