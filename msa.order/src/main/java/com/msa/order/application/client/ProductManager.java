package com.msa.order.application.client;

import com.msa.order.application.client.dto.CompanyData;
import com.msa.order.application.client.dto.ProductStockRequest;
import com.msa.order.application.client.dto.ProductData;
import com.msa.order.presentation.response.ApiResponse;

import java.util.UUID;

public interface ProductManager {

	ApiResponse<ProductData> reduceStock(UUID itemId, ProductStockRequest stock);

	ApiResponse<ProductData> restoreStock(UUID itemId, ProductStockRequest stock);

	ApiResponse<CompanyData> getCompanyInfo(UUID companyId);

}