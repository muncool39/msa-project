package com.msa.order.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.order.application.client.dto.CompanyData;
import com.msa.order.application.client.dto.ProductData;
import com.msa.order.application.client.dto.ProductStockRequest;
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
