package com.msa.company.presentation.controller;

import com.msa.company.application.service.ProductService;
import com.msa.company.presentation.request.ProductRequest;
import com.msa.company.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    public final ProductService productService;

    // 상품 생성
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER','COMPANY_MANAGER')")
    public ApiResponse<Void> createProduct(
            @RequestBody @Valid ProductRequest productRequest,
            @AuthenticationPrincipal UserDetails userDetail) {
        Long userId = Long.valueOf(userDetail.getUsername());
        String role = userDetail.getAuthorities().iterator().next().getAuthority();
        productService.createProduct(productRequest, userId, role);
        return ApiResponse.success();
    }
}
