package com.msa.delivery.application.client;

import com.msa.delivery.application.client.dto.request.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.response.DeliveryWorkersData;
import com.msa.delivery.application.client.dto.response.ShipperData;
import com.msa.delivery.application.client.dto.response.UserData;
import com.msa.delivery.presentation.response.ApiResponse;

public interface UserManager {

	ApiResponse<UserData> getUserInfo(Long userId);

	ApiResponse<DeliveryWorkersData> assignDeliveryWorkers(GetDeliveryWorkersRequest request);

	ApiResponse<ShipperData> getShipperInfo(Long deliverId);
}
