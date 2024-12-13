package com.msa.order.presentation;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msa.order.application.service.CreateOrderService;
import com.msa.order.application.service.GetOrderService;
import com.msa.order.application.service.OrderModificationService;
import com.msa.order.application.service.dto.OrderDetails;
import com.msa.order.config.UserDetailImpl;
import com.msa.order.domain.entity.enums.UserRole;
import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.request.UpdateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;
import com.msa.order.presentation.response.GetOrderResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final CreateOrderService createOrderService;
	private final GetOrderService getOrderService;
	private final OrderModificationService orderModificationService;

	@PostMapping
	public ApiResponse<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		createOrderService.createOrder(request);
		return ApiResponse.success();
	}

	@GetMapping("/{id}")
	public ApiResponse<GetOrderResponse> getOrder(@PathVariable(name = "id") UUID orderId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {
		OrderDetails orderDetails = getOrderService.getOrder(orderId, userDetail);
		return ApiResponse.success(GetOrderResponse.from(orderDetails));
	}

	@PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
	@PatchMapping("/{id}")
	public ApiResponse<Void> updateOrder(@PathVariable(name = "id") UUID orderId,
		@Valid @RequestBody UpdateOrderRequest request,
		@AuthenticationPrincipal UserDetailImpl userDetail) {

		String userId = userDetail.getUsername();
		UserRole role = userDetail.getUserRole();
		orderModificationService.updateOrder(orderId, request, userId, role);
		return ApiResponse.success();
	}

	@PatchMapping("/cancel/{id}")
	public ApiResponse<Void> cancelOrder(@PathVariable(name = "id") UUID orderId) {
		orderModificationService.cancelOrder(orderId);
		return ApiResponse.success();
	}

	@PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
	@DeleteMapping("{id}")
	public ApiResponse<Void> deleteOrder(@PathVariable(name = "id") UUID orderId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {

		String userId = userDetail.getUsername();
		UserRole role = userDetail.getUserRole();
		orderModificationService.deleteOrder(orderId, userId, role);
		return ApiResponse.success();
	}
}