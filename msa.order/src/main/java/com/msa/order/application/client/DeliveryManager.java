package com.msa.order.application.client;

import java.util.UUID;

import com.msa.order.application.client.dto.request.CreateDeliveryRequest;
import com.msa.order.application.client.dto.response.DeliveryData;
import com.msa.order.presentation.response.ApiResponse;

public interface DeliveryManager {

	ApiResponse<DeliveryData> createDelivery(CreateDeliveryRequest request);

	DeliveryData getDeliveryInfo(UUID orderId);
}