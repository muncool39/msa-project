package com.msa.delivery.infrastructure;

import org.springframework.stereotype.Component;

import com.msa.delivery.application.client.dto.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.UserData;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "User-Client")
@Component("userClientFallback")
public class UserClientFallback implements UserClient {
	@Override
	public UserData getUserInfo(Long userId) {
		return null;
	}

	@Override
	public DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request) {
		return null;
	}
}
