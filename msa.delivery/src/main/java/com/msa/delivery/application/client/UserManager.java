package com.msa.delivery.application.client;

import com.msa.delivery.application.client.dto.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.UserData;
import com.msa.delivery.presentation.response.ApiResponse;

public interface UserManager {

	ApiResponse<UserData> getUserInfo(Long userId);

	DeliveryWorkersData assignDeliveryWorkers(GetDeliveryWorkersRequest request);
}
