package com.msa.delivery.application.client;

import com.msa.delivery.application.client.dto.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.UserData;

public interface UserManager {

	UserData getUserInfo(Long userId);

	DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request);
}
