package com.msa.order.presentation;

import com.msa.order.application.service.CreateOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final CreateOrderService createOrderService;

	@PostMapping
	public ApiResponse<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {

		createOrderService.createOrder(request);

		return ApiResponse.success();
	}
}
