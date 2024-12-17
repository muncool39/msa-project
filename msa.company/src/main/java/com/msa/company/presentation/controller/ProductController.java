package com.msa.company.presentation.controller;

import com.msa.company.application.service.ProductService;
import com.msa.company.infrastructure.config.dto.UserDetailImpl;
import com.msa.company.presentation.request.StockRequest;
import com.msa.company.presentation.request.UpdateProductRequest;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.PageResponse;
import com.msa.company.application.dto.response.ProductDetailResponse;
import com.msa.company.application.dto.response.ProductListResponse;
import com.msa.company.application.dto.response.StockResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("companies/products")
public class ProductController {

    public final ProductService productService;

    // 상품 전체 조회
    @GetMapping
    public ApiResponse<PageResponse<ProductListResponse>> getListProducts(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String companyId,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String isOutOfStock) {
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Order.desc(sortBy)));

        return ApiResponse.success(PageResponse.of(
                productService.getListProducts(companyId, companyName, name, isOutOfStock, page))
        );
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
                                           @AuthenticationPrincipal UserDetailImpl userDetails) {
        Long userId = userDetails.userId();
        String role = userDetails.role();
        productService.updateProduct(id, request, userId, role);
        return ApiResponse.success();
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
    public ApiResponse<Void> deleteProduct(@PathVariable("id") UUID id,
                                           @AuthenticationPrincipal UserDetailImpl userDetails) {
        Long userId = userDetails.userId();
        String role = userDetails.role();
        productService.deleteProduct(id, userId, role);
        return ApiResponse.success();
    }

    // 상품 재고 감소
    @PostMapping("/{id}/reduce-stock")
    public ApiResponse<StockResponse> decreaseStock(@PathVariable("id") UUID id,
                                                    @RequestBody StockRequest stockRequest) {
        StockResponse response = productService.decreaseStock(id, stockRequest.getStock());
        return ApiResponse.success(response);
    }

    // 상품 재고 복원
    @PostMapping("/{id}/restore-stock")
    public ApiResponse<StockResponse> restoreStock(@PathVariable("id") UUID id,
                                                   @RequestBody StockRequest stockRequest) {
        StockResponse response = productService.restoreStock(id, stockRequest.getStock());
        return ApiResponse.success(response);
    }
}
