package com.msa.company.presentation.controller;

import com.msa.company.application.service.ProductService;
import com.msa.company.domain.entity.Product;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.ProductDetailResponse;
import com.msa.company.presentation.response.ProductListResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    // 상품 단건 조회
    @GetMapping("/products/{id}")
    public ApiResponse<ProductDetailResponse> getDetailProduct(@PathVariable("id") UUID id) {
        ProductDetailResponse product = productService.getDetailProduct(id);
        return ApiResponse.success(product);
    }
}
