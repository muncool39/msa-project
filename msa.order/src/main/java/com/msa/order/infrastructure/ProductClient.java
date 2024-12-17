package com.msa.order.infrastructure;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.msa.order.application.client.ProductManager;
import com.msa.order.application.client.dto.CompanyData;
import com.msa.order.application.client.dto.ProductData;
import com.msa.order.application.client.dto.ProductStockRequest;
import com.msa.order.config.FeignConfiguration;
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
