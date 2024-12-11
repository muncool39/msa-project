package com.msa.order.infrastructure;

import com.msa.order.application.service.DeliveryManager;
import com.msa.order.application.service.dto.CreateDeliveryRequest;
import com.msa.order.application.service.dto.DeliveryData;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "delivery-service", fallback = DeliveryClientFallback.class, qualifiers = "deliveryClient")
public interface DeliveryClient extends DeliveryManager {

  @PostMapping("/deliveries")
  DeliveryData createDelivery(@RequestBody CreateDeliveryRequest request);

  @GetMapping("/deliveries/order/{id}")
  DeliveryData getDeliveryInfo(UUID orderId);


}
