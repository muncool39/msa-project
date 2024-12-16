package com.msa.order.infrastructure.client.impl;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.msa.order.application.client.DeliveryManager;
import com.msa.order.application.client.dto.request.CreateDeliveryRequest;
import com.msa.order.application.client.dto.response.DeliveryData;
import com.msa.order.infrastructure.config.feign.FeignConfiguration;
import com.msa.order.infrastructure.client.fallback.DeliveryClientFallback;
import com.msa.order.presentation.response.ApiResponse;

@FeignClient(
    name = "delivery-service",
    fallback = DeliveryClientFallback.class,
    qualifiers = "deliveryClient",
	configuration = FeignConfiguration.class)
public interface DeliveryClient extends DeliveryManager {

	@PostMapping(value = "/deliveries")
	ApiResponse<DeliveryData> createDelivery(@RequestBody CreateDeliveryRequest request);

	@GetMapping("/deliveries/order/{id}")
	DeliveryData getDeliveryInfo(@PathVariable(name = "id") UUID orderId);

}