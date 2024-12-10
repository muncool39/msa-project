package com.msa.order.infrastructure;

import com.msa.order.application.service.dto.ProductStockRequest;
import com.msa.order.application.service.dto.ProductStockResponse;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Product-Client")
@Component("productFallback")
public class ProductClientFallback implements ProductClient {

  @Override
  public ProductStockResponse reduceStock(UUID id, ProductStockRequest stock) {
    log.error("Failed to reduce stock for product: {}", id);
    return new ProductStockResponse(null, null);
  }

  @Override
  public ProductStockResponse restoreStock(UUID id, ProductStockRequest stock) {
    return null;
  }
}
