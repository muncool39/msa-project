package com.msa.order.infrastructure;

import com.msa.order.application.service.ProductManager;
import com.msa.order.application.service.dto.CompanyData;
import com.msa.order.application.service.dto.ProductStockRequest;
import com.msa.order.application.service.dto.ProductStockData;
import com.msa.order.config.FeignConfiguration;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

// 응용 계층의 인터페이스인 ProductManager 를 상속받아 DIP 적용
@FeignClient(
    name = "company-service",
    fallback = ProductClientFallback.class,
    qualifiers = "productClient",
    configuration = FeignConfiguration.class)
public interface ProductClient extends ProductManager {

  @PatchMapping("/products/{id}/reduce-stock")
  ProductStockData reduceStock(@PathVariable(name = "id") UUID id,
      @RequestBody ProductStockRequest stock);

  @PatchMapping("/products/{id}/restore-stock")
  ProductStockData restoreStock(@PathVariable(name = "id") UUID id,
      @RequestBody ProductStockRequest stock);

  @GetMapping("/companies/{id}")
  CompanyData getCompanyInfo(@PathVariable(name = "id") UUID companyId);

}
