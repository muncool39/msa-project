package com.msa.order.infrastructure.client.fallback;

import com.msa.order.application.client.dto.request.CreateDeliveryRequest;
import com.msa.order.application.client.dto.response.DeliveryData;
import com.msa.order.infrastructure.client.impl.DeliveryClient;
import com.msa.order.presentation.response.ApiResponse;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Delivery-Client")
@Component("deliveryFallback")
public class DeliveryClientFallback implements DeliveryClient {

  @Override
  public ApiResponse<DeliveryData> createDelivery(CreateDeliveryRequest request) {
    log.error("Created Deliver Service failed");
    return null;
  }

  @Override
  public DeliveryData getDeliveryInfo(UUID orderId) {
    log.error("Get Deliver Service failed");
    return new DeliveryData(null, null,null,null, null, null);
  }

}
