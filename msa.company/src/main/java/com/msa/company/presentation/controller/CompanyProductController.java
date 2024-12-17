package com.msa.company.presentation.controller;

import com.msa.company.application.service.CompanyProductService;
import com.msa.company.infrastructure.config.dto.UserDetailImpl;
import com.msa.company.presentation.request.CreateProductRequest;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.PageResponse;
import com.msa.company.application.dto.response.ProductListResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ApiResponse<PageResponse<ProductListResponse>> getProductsByCompany(
            @PathVariable("companyId") UUID companyId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String isOutOfStock) {
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Order.desc(sortBy)));

        return ApiResponse.success(PageResponse.of(
                companyProductService.getProductsByCompany(companyId, name, isOutOfStock, page))
        );
    }
}
