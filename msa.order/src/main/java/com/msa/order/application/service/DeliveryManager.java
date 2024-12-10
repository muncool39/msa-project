package com.msa.order.application.service;

import com.msa.order.application.service.dto.CreateDeliveryRequest;
import com.msa.order.application.service.dto.CreateDeliveryResponse;

public interface DeliveryManager {

	CreateDeliveryResponse createDelivery(CreateDeliveryRequest request);

}