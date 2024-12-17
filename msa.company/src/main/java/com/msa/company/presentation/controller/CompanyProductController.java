package com.msa.company.presentation.controller;

import com.msa.company.application.service.CompanyProductService;
import com.msa.company.config.dto.UserDetailImpl;
import com.msa.company.presentation.request.CreateProductRequest;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.ProductListResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies/{companyId}/products")
@RequiredArgsConstructor
public class CompanyProductController {

    private final CompanyProductService companyProductService;

    // 상품 생성
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER','COMPANY_MANAGER')")
    public ApiResponse<Void> createProduct(
            @PathVariable("companyId") UUID companyId,
            @RequestBody @Valid CreateProductRequest productRequest,
            @AuthenticationPrincipal UserDetailImpl userDetails) {
        Long userId = userDetails.userId();
        String role = userDetails.role();
        companyProductService.createProduct(companyId, productRequest, userId, role);
        return ApiResponse.success();
    }

    // 업체 내 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductListResponse>> getProductsByCompany(
            @PathVariable("companyId") UUID companyId) {
        List<ProductListResponse> products = companyProductService.getProductsByCompany(companyId);
        return ResponseEntity.ok(products);
    }
}
