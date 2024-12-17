package com.msa.order.application.client;

import com.msa.order.application.client.dto.response.CompanyData;
import com.msa.order.application.client.dto.request.ProductStockRequest;
import com.msa.order.application.client.dto.response.ProductData;
import com.msa.order.presentation.response.ApiResponse;

import java.util.UUID;

public interface ProductManager {

	ApiResponse<ProductData> reduceStock(UUID itemId, ProductStockRequest stock);

	ApiResponse<ProductData> restoreStock(UUID itemId, ProductStockRequest stock);

	ApiResponse<CompanyData> getCompanyInfo(UUID companyId);

}