package com.msa.company.presentation.controller;

import com.msa.company.application.service.CompanyService;
import com.msa.company.presentation.request.CreateCompanyRequest;
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
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    public final CompanyService companyService;

    // 업체 생성
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
    public ApiResponse<Void> createCompany(
            @Valid @RequestBody CreateCompanyRequest createCompanyRequest,
            @AuthenticationPrincipal UserDetails userDetails) {
        String userId = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        System.out.println("User role: " + role);
        companyService.createCompany(createCompanyRequest, userId, role);
        return ApiResponse.success();
    }
}
