package com.msa.order.application.service;

import com.msa.order.application.service.dto.CreateDeliveryRequest;
import com.msa.order.application.service.dto.DeliveryData;
import java.util.UUID;

public interface DeliveryManager {

	DeliveryData createDelivery(CreateDeliveryRequest request);

  DeliveryData getDeliveryInfo(UUID orderId);
}