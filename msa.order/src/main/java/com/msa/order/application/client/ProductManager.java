package com.msa.order.application.client;

import com.msa.order.application.client.dto.CompanyData;
import com.msa.order.application.client.dto.ProductStockRequest;
import com.msa.order.application.client.dto.ProductStockData;
import java.util.UUID;

public interface ProductManager {

	ProductStockData reduceStock(UUID itemId, ProductStockRequest stock);

	ProductStockData restoreStock(UUID itemId, ProductStockRequest stock);

	CompanyData getCompanyInfo(UUID companyId);

}