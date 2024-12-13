package com.msa.delivery.infrastructure;

import org.springframework.stereotype.Component;

import com.msa.delivery.application.dto.DeliveryWorkersData;
import com.msa.delivery.application.dto.GetDeliveryWorkersRequest;

@Component("deliveryAssignFallback")
public class DeliveryAssignClientFallback implements DeliveryAssignerClient{

	@Override
	public DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request) {
		return null;
	}
}
