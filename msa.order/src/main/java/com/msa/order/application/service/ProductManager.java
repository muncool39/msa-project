package com.msa.order.application.service;

import com.msa.order.application.service.dto.CompanyData;
import com.msa.order.application.service.dto.ProductStockRequest;
import com.msa.order.application.service.dto.ProductStockData;
import java.util.UUID;

public interface ProductManager {

	ProductStockData reduceStock(UUID itemId, ProductStockRequest stock);

	ProductStockData restoreStock(UUID itemId, ProductStockRequest stock);

	CompanyData getCompanyInfo(UUID companyId);

}