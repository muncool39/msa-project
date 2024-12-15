package com.msa.company.presentation.controller;

import com.msa.company.application.service.ProductService;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.ProductListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {

    public final ProductService productService;

    // 상품 전체 조회
    @GetMapping("/products")
    public ApiResponse<List<ProductListResponse>> getListProducts() {
        List<ProductListResponse> products = productService.getListProducts();
        return ApiResponse.success(products);
    }

}
