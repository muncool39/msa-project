package com.msa.order.infrastructure;

import com.msa.order.application.service.dto.CreateDeliveryRequest;
import com.msa.order.application.service.dto.CreateDeliveryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Delivery-Client")
@Component("deliveryFallback")
public class DeliveryClientFallback implements DeliveryClient {

  @Override
  public CreateDeliveryResponse createDelivery(CreateDeliveryRequest request) {
    log.error("Created Deliver Service failed");
    return new CreateDeliveryResponse(null);
  }
}
