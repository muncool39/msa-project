package com.msa.order.application.client;

import java.util.UUID;

import com.msa.order.application.client.dto.CreateDeliveryRequest;
import com.msa.order.application.client.dto.DeliveryData;
import com.msa.order.presentation.response.ApiResponse;

public interface DeliveryManager {

	ApiResponse<DeliveryData> createDelivery(CreateDeliveryRequest request);

	DeliveryData getDeliveryInfo(UUID orderId);
}