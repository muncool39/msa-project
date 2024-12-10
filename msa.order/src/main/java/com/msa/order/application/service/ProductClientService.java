package com.msa.order.application.service;

import java.util.List;

public interface ProductClientService {

	void getProductsByIds(List<Long> productId);

}
