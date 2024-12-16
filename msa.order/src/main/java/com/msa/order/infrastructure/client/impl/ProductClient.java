package com.msa.order.infrastructure.client.impl;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.msa.order.application.client.ProductManager;
import com.msa.order.application.client.dto.response.CompanyData;
import com.msa.order.application.client.dto.response.ProductData;
import com.msa.order.application.client.dto.request.ProductStockRequest;
import com.msa.order.infrastructure.config.feign.FeignConfiguration;
import com.msa.order.infrastructure.client.fallback.ProductClientFallback;
import com.msa.order.presentation.response.ApiResponse;

@FeignClient(
    name = "company-service",
    fallback = ProductClientFallback.class,
    qualifiers = "productClient",
    configuration = FeignConfiguration.class)
public interface ProductClient extends ProductManager {

  @PostMapping("/products/{id}/reduce-stock")
  ApiResponse<ProductData> reduceStock(@PathVariable(name = "id") UUID id,
      @RequestBody ProductStockRequest stock);

  @PostMapping("/products/{id}/restore-stock")
  ApiResponse<ProductData> restoreStock(@PathVariable(name = "id") UUID id,
      @RequestBody ProductStockRequest stock);

  @GetMapping("/companies/{id}")
  ApiResponse<CompanyData> getCompanyInfo(@PathVariable(name = "id") UUID companyId);

}
