package com.msa.order.infrastructure;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.msa.order.application.client.DeliveryManager;
import com.msa.order.application.client.dto.CreateDeliveryRequest;
import com.msa.order.application.client.dto.DeliveryData;

@FeignClient(
    name = "delivery-service",
    fallback = DeliveryClientFallback.class,
    qualifiers = "deliveryClient")
public interface DeliveryClient extends DeliveryManager {

	@PostMapping("/deliveries")
	DeliveryData createDelivery(@RequestBody CreateDeliveryRequest request);

	@GetMapping("/deliveries/order/{id}")
	DeliveryData getDeliveryInfo(@PathVariable(name = "id") UUID orderId);

}