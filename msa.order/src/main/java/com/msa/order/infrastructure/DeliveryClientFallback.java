package com.msa.order.infrastructure;

import com.msa.order.application.service.dto.CreateDeliveryRequest;
import com.msa.order.application.service.dto.DeliveryData;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Delivery-Client")
@Component("deliveryFallback")
public class DeliveryClientFallback implements DeliveryClient {

  @Override
  public DeliveryData createDelivery(CreateDeliveryRequest request) {
    log.error("Created Deliver Service failed");
    return new DeliveryData(null, null, null, null, null, null, null);
  }

  @Override
  public DeliveryData getDeliveryInfo(UUID orderId) {
    return null;
  }
}
