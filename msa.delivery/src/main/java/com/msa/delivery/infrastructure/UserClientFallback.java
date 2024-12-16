package com.msa.delivery.infrastructure;

import org.springframework.stereotype.Component;

import com.msa.delivery.application.client.dto.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.UserData;
import com.msa.delivery.presentation.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "User-Client")
@Component("userClientFallback")
public class UserClientFallback implements UserClient {
	@Override
	public ApiResponse<UserData> getUserInfo(Long userId) {
		log.error("[FeignClient] 유저 정보 호출 에러 발생");
		return null;
	}

	@Override
	public DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request) {
		return null;
	}
}
