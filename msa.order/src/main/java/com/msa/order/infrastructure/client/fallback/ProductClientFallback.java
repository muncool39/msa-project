package com.msa.order.infrastructure.client.fallback;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.order.application.client.dto.response.CompanyData;
import com.msa.order.application.client.dto.response.ProductData;
import com.msa.order.application.client.dto.request.ProductStockRequest;
import com.msa.order.infrastructure.client.impl.ProductClient;
import com.msa.order.presentation.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Product-Client")
@Component("productFallback")
public class ProductClientFallback implements ProductClient {

  @Override
  public ApiResponse<ProductData> reduceStock(UUID id, ProductStockRequest stock) {
    log.error("Failed to reduce stock for product: {}", id);
    return null;
  }

  @Override
  public ApiResponse<ProductData> restoreStock(UUID id, ProductStockRequest stock) {
    return null;
  }

  @Override
  public ApiResponse<CompanyData> getCompanyInfo(UUID companyId) {
    log.error("Failed to get company detail info companyId = {}", companyId);
    return null;
  }
}
