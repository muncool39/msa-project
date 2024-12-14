package com.msa.delivery.presentation;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.delivery.application.service.CreateDeliveryService;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.presentation.request.CreateDeliveryRequest;
import com.msa.delivery.presentation.response.ApiResponse;
import com.msa.delivery.presentation.response.CreateDeliveryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

	private final CreateDeliveryService createDeliveryService;

	@PreAuthorize("hasAuthority('MASTER')")
	@PostMapping
	public ApiResponse<CreateDeliveryResponse> createDelivery(@Valid @RequestBody CreateDeliveryRequest request) {
		Delivery delivery = createDeliveryService.createDelivery(request);
		CreateDeliveryResponse response = CreateDeliveryResponse.from(delivery);
		return ApiResponse.success(response);
	}

}
