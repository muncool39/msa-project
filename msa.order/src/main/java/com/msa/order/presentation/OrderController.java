package com.msa.order.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.msa.order.application.service.CreateOrderService;
import com.msa.order.application.service.ModifyOrderService;
import com.msa.order.application.service.ReadOrderService;
import com.msa.order.config.UserDetailImpl;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.entity.enums.UserRole;
import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.request.UpdateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;
import com.msa.order.presentation.response.PageResponse;
import com.msa.order.presentation.response.ReadOrderResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

	private final CreateOrderService createOrderService;
	private final ReadOrderService readOrderService;
	private final ModifyOrderService modifyOrderService;

	@PostMapping
	public ApiResponse<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		createOrderService.createOrder(request);
		return ApiResponse.success();
	}

	@GetMapping
	public ApiResponse<PageResponse<ReadOrderResponse>> getOrders(
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable, // 사이즈 10, 30, 50 | 정렬은 생성일순, 수정일순
		@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
		@RequestParam(name = "search", required = false) String search, @AuthenticationPrincipal UserDetailImpl userDetail) {

		int pageSize = pageable.getPageSize();
		if (pageSize != 10 && pageSize != 30 && pageSize != 50) {pageSize = 10;}
		if (!List.of("createdAt", "updatedAt").contains(sortBy)) {sortBy = "createdAt";}
		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageSize, Sort.Direction.DESC, sortBy);

		Page<Order> orders = readOrderService.getOrders(paging, search, userDetail);
		return ApiResponse.success(ReadOrderResponse.pageOf(orders));
	}

	@GetMapping("/{id}")
	public ApiResponse<ReadOrderResponse> getOrder(@PathVariable(name = "id") UUID orderId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {
		Order order = readOrderService.getOrder(orderId, userDetail);
		return ApiResponse.success(ReadOrderResponse.from(order));
	}

	@PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
	@PatchMapping("/{id}")
	public ApiResponse<Void> updateOrder(@PathVariable(name = "id") UUID orderId,
		@Valid @RequestBody UpdateOrderRequest request,
		@AuthenticationPrincipal UserDetailImpl userDetail) {

		String userId = userDetail.getUsername();
		UserRole role = userDetail.getUserRole();
		modifyOrderService.updateOrder(orderId, request, userId, role);
		return ApiResponse.success();
	}

	@PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER', 'COMPANY_MANAGER')")
	@PatchMapping("/cancel/{id}")
	public ApiResponse<Void> cancelOrder(@PathVariable(name = "id") UUID orderId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {

		String userId = userDetail.getUsername();
		UserRole role = userDetail.getUserRole();
		modifyOrderService.cancelOrder(orderId, userId, role);
		return ApiResponse.success();
	}

	@PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
	@DeleteMapping("{id}")
	public ApiResponse<Void> deleteOrder(@PathVariable(name = "id") UUID orderId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {

		String userId = userDetail.getUsername();
		UserRole role = userDetail.getUserRole();
		modifyOrderService.deleteOrder(orderId, userId, role);
		return ApiResponse.success();
	}
}