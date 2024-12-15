package com.msa.delivery.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.msa.delivery.application.service.CreateDeliveryService;
import com.msa.delivery.application.service.ReadDeliveryService;
import com.msa.delivery.application.service.UpdateDeliveryService;
import com.msa.delivery.config.UserDetailImpl;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.enums.UserRole;
import com.msa.delivery.presentation.request.CreateDeliveryRequest;
import com.msa.delivery.presentation.request.UpdateDeliveryRequest;
import com.msa.delivery.presentation.response.ApiResponse;
import com.msa.delivery.presentation.response.CreateDeliveryResponse;
import com.msa.delivery.presentation.response.DeliveryDataResponse;
import com.msa.delivery.presentation.response.PageResponse;
import com.msa.delivery.presentation.response.ReadDeliveryResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

	private final CreateDeliveryService createDeliveryService;
	private final ReadDeliveryService readDeliveryService;
	private final UpdateDeliveryService updateDeliveryService;

	@PreAuthorize("hasAuthority('MASTER')")
	@PostMapping
	public ApiResponse<CreateDeliveryResponse> createDelivery(@Valid @RequestBody CreateDeliveryRequest request) {
		Delivery delivery = createDeliveryService.createDelivery(request);
		CreateDeliveryResponse response = CreateDeliveryResponse.from(delivery);
		return ApiResponse.success(response);
	}

	@GetMapping
	public ApiResponse<PageResponse<ReadDeliveryResponse>> getDeliveries(
		@PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
		@RequestParam(required = false, defaultValue = "createdAt") String sortBy,
		@RequestParam(required = false) String search, @AuthenticationPrincipal UserDetailImpl userDetail) {

		int pageSize = pageable.getPageSize();
		if (pageSize != 10 && pageSize != 30 && pageSize != 50) {pageSize = 10;}
		if (!List.of("createdAt", "updatedAt").contains(sortBy)) {sortBy = "createdAt";}
		Pageable paging = PageRequest.of(pageable.getPageNumber(), pageSize, Sort.Direction.DESC, sortBy);

		Page<Delivery> deliveryies = readDeliveryService.getDeliveries(paging, search, userDetail);
		return ApiResponse.success(ReadDeliveryResponse.pageOf(deliveryies));

	}

	@GetMapping("/{id}")
	public ApiResponse<ReadDeliveryResponse> getDelivery(@PathVariable(name = "id") UUID deliveryId,
		@AuthenticationPrincipal UserDetailImpl userDetail) {
		Delivery delivery = readDeliveryService.getDelivery(deliveryId, userDetail);
		ReadDeliveryResponse response = ReadDeliveryResponse.from(delivery);
		return ApiResponse.success(response);
	}

	@PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER', 'DELIVERY_MANAGER')")
	@PatchMapping("/{id}")
	public ApiResponse<Void> updateDelivery(@PathVariable(name = "id") UUID deliveryId,
		@Valid @RequestBody UpdateDeliveryRequest request,
		@AuthenticationPrincipal UserDetailImpl userDetail) {

		String userId = userDetail.getUsername();
		UserRole role = userDetail.getUserRole();
		updateDeliveryService.updateDelivery(deliveryId, request, userId, role);
		return ApiResponse.success();
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.CONFLICT)
	public ApiResponse<String> deleteDelivery(@PathVariable(name = "id") UUID deliveryId) {
		return ApiResponse.fail("배송 삭제는 불가합니다. ");
	}

	// 서버통신용 조회 API
	@GetMapping("/order/{id}")
	public DeliveryDataResponse getDeliveryByOrder(@PathVariable(name = "id") UUID orderId) {
		Delivery delivery = readDeliveryService.getDeliveryByOrderId(orderId);
		return DeliveryDataResponse.from(delivery);
	}
}
