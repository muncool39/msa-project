package com.msa.company.presentation.controller;

import com.msa.company.application.service.CompanyProductService;
import com.msa.company.presentation.request.CreateProductRequest;
import com.msa.company.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            @AuthenticationPrincipal Long userId,
            @AuthenticationPrincipal String role) {
        companyProductService.createProduct(companyId, productRequest, userId, role);
        return ApiResponse.success();
    }
}
