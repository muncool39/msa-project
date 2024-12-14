package com.msa.delivery.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.msa.delivery.application.dto.DeliveryWorkersData;
import com.msa.delivery.application.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.application.service.DeliveryAssigner;
import com.msa.delivery.config.FeignConfiguration;

@FeignClient(
	name = "user-service", //
	fallback = DeliveryAssignClientFallback.class,
	qualifiers = "deliveryAssignClient",
	configuration = FeignConfiguration.class)
public interface DeliveryAssignerClient extends DeliveryAssigner {

	@GetMapping("/users") // TODO URL 설정 필요
	DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request);
}
