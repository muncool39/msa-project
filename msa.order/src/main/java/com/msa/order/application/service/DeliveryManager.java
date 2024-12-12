package com.msa.order.application.service;

import java.util.UUID;

import com.msa.order.application.service.dto.CreateDeliveryRequest;
import com.msa.order.application.service.dto.DeliveryData;

public interface DeliveryManager {

	DeliveryData createDelivery(CreateDeliveryRequest request);

	DeliveryData getDeliveryInfo(UUID orderId);
}