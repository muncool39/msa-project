package com.msa.order.application.client;

import java.util.UUID;

import com.msa.order.application.client.dto.CreateDeliveryRequest;
import com.msa.order.application.client.dto.DeliveryData;

public interface DeliveryManager {

	DeliveryData createDelivery(CreateDeliveryRequest request);

	DeliveryData getDeliveryInfo(UUID orderId);
}