package com.msa.order.application.service;

import com.msa.order.application.service.dto.ProductStockRequest;
import com.msa.order.application.service.dto.ProductStockResponse;
import java.util.UUID;

public interface ProductManager {

	ProductStockResponse reduceStock(UUID itemId, ProductStockRequest stock);

	ProductStockResponse restoreStock(UUID itemId, ProductStockRequest stock);

}