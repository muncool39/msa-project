package com.msa.delivery.application.service;

import com.msa.delivery.application.dto.DeliveryWorkersData;
import com.msa.delivery.application.dto.GetDeliveryWorkersRequest;

public interface DeliveryAssigner {

	DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request);
}
