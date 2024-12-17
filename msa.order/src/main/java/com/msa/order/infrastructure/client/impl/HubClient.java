package com.msa.order.infrastructure.client.impl;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.msa.order.application.client.HubManager;
import com.msa.order.application.client.dto.response.HubData;
import com.msa.order.infrastructure.config.feign.FeignConfiguration;
import com.msa.order.infrastructure.client.fallback.HubClientFallback;

@FeignClient(
	name = "hub-service",
	fallback = HubClientFallback.class,
	qualifiers = "hubClient",
	configuration = FeignConfiguration.class)
public interface HubClient extends HubManager {

	@GetMapping("/hubs/{hub_id}")
	HubData getHubInfo(@PathVariable(name = "hub_id") UUID hubId);

}
