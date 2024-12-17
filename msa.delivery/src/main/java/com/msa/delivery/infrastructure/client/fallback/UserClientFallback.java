package com.msa.delivery.infrastructure.client.fallback;

import org.springframework.stereotype.Component;

import com.msa.delivery.application.client.dto.request.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.response.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.response.ShipperData;
import com.msa.delivery.application.client.dto.response.UserData;
import com.msa.delivery.infrastructure.client.impl.UserClient;
import com.msa.delivery.presentation.response.ApiResponse;

@Component("userClientFallback")
public class UserClientFallback implements UserClient {
	@Override
	public ApiResponse<UserData> getUserInfo(Long userId) {
		return ApiResponse.fail(null);
	}

	@Override
	public ApiResponse<DeliveryWorkersData> assignDeliveryWorkers(GetDeliveryWorkersRequest request) {
		return ApiResponse.fail(null);
	}

	@Override
	public ApiResponse<ShipperData> getShipperInfo(Long deliverId) {
		return ApiResponse.fail(null);
	}
}
