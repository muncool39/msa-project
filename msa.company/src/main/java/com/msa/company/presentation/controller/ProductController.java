package com.msa.company.presentation.controller;

import com.msa.company.application.service.ProductService;
import com.msa.company.presentation.request.StockRequest;
import com.msa.company.presentation.request.UpdateProductRequest;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.ProductDetailResponse;
import com.msa.company.presentation.response.ProductListResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    public final ProductService productService;

    // 상품 전체 조회
    @GetMapping
    public ApiResponse<List<ProductListResponse>> getListProducts() {
        List<ProductListResponse> products = productService.getListProducts();
        return ApiResponse.success(products);
    }

    // 상품 단건 조회
    @GetMapping("/{id}")
    public ApiResponse<ProductDetailResponse> getDetailProduct(@PathVariable("id") UUID id) {
        ProductDetailResponse product = productService.getDetailProduct(id);
        return ApiResponse.success(product);
    }

    // 상품 수정
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER','COMPANY_MANAGER')")
    public ApiResponse<Void> updateProduct(@PathVariable("id") UUID id,
                                           @RequestBody UpdateProductRequest request,
                                           @AuthenticationPrincipal Long userId,
                                           @AuthenticationPrincipal String role) {
        productService.updateProduct(id, request, userId, role);
        return ApiResponse.success();
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
    public ApiResponse<Void> deleteProduct(@PathVariable("id") UUID id,
                                           @AuthenticationPrincipal Long userId,
                                           @AuthenticationPrincipal String role) {
        productService.deleteProduct(id, userId, role);
        return ApiResponse.success();
    }

    // 상품 재고 감소
    @PatchMapping("/{id}/reduce-stock")
    public ApiResponse<Void> decreaseStock(@PathVariable("id") UUID id,
                                           @RequestBody StockRequest stockRequest) {
        productService.decreaseStock(id, stockRequest.getStock());
        return ApiResponse.success();
    }

    // 상품 재고 복원
    @PatchMapping("/{id}/restore-stock")
    public ApiResponse<Void> restoreStock(@PathVariable("id") UUID id,
                                          @RequestBody StockRequest stockRequest) {
        productService.restoreStock(id, stockRequest.getStock());
        return ApiResponse.success();
    }
}
