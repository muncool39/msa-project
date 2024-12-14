package com.msa.delivery.presentation;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.delivery.application.service.CreateDeliveryService;
import com.msa.delivery.application.service.ReadDeliveryService;
import com.msa.delivery.config.UserDetailImpl;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.presentation.request.CreateDeliveryRequest;
import com.msa.delivery.presentation.response.ApiResponse;
import com.msa.delivery.presentation.response.CreateDeliveryResponse;
import com.msa.delivery.presentation.response.DeliveryDataResponse;
import com.msa.delivery.presentation.response.ReadDeliveryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

	private final CreateDeliveryService createDeliveryService;
	private final ReadDeliveryService readDeliveryService;

	@PreAuthorize("hasAuthority('MASTER')")
	@PostMapping
	public ApiResponse<CreateDeliveryResponse> createDelivery(@Valid @RequestBody CreateDeliveryRequest request) {
		Delivery delivery = createDeliveryService.createDelivery(request);
		CreateDeliveryResponse response = CreateDeliveryResponse.from(delivery);
		return ApiResponse.success(response);
	}

	@GetMapping("/{id}")
	public ApiResponse<ReadDeliveryResponse> getDelivery(@PathVariable("id") UUID deliveryId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {
		Delivery delivery = readDeliveryService.getDelivery(deliveryId, userDetail);
		ReadDeliveryResponse response = ReadDeliveryResponse.from(delivery);
		return ApiResponse.success(response);
	}

	@DeleteMapping("/{id}")
	public ApiResponse<String> deleteDelivery(@PathVariable("id") UUID deliveryId) {
		return ApiResponse.success("배송 삭제는 불가합니다. ");
	}

	// 서버통신용 조회 API
	@GetMapping("/order/{id}")
	public DeliveryDataResponse getDeliveryByOrder(@PathVariable("id") UUID orderId) {
		Delivery delivery = readDeliveryService.getDeliveryByOrderId(orderId);
		return DeliveryDataResponse.from(delivery);
	}
}
