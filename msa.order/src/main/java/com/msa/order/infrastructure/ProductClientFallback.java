package com.msa.order.infrastructure;

import com.msa.order.application.service.dto.CompanyData;
import com.msa.order.application.service.dto.ProductStockRequest;
import com.msa.order.application.service.dto.ProductStockData;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j(topic = "Product-Client")
@Component("productFallback")
public class ProductClientFallback implements ProductClient {

  @Override
  public ProductStockData reduceStock(UUID id, ProductStockRequest stock) {
    log.error("Failed to reduce stock for product: {}", id);
    return new ProductStockData(null, null);
  }

  @Override
  public ProductStockData restoreStock(UUID id, ProductStockRequest stock) {
    return null;
  }

  @Override
  public CompanyData getCompanyInfo(UUID companyId) {
    return null;
  }
}
