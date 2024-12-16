package com.msa.company.presentation.controller;

import com.msa.company.application.service.CompanyService;
import com.msa.company.application.service.CreateCompanyService;
import com.msa.company.application.service.UpdateCompanyService;
import com.msa.company.config.dto.UserDetailImpl;
import com.msa.company.presentation.request.CreateCompanyRequest;
import com.msa.company.presentation.request.UpdateCompanyRequest;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.CompanyDetailResponse;
import com.msa.company.presentation.response.CompanyListResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    public final CreateCompanyService createCompanyService;
    public final UpdateCompanyService updateCompanyService;
    public final CompanyService companyService;

    // 업체 생성
    @PostMapping
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
    public ApiResponse<Void> createCompany(
            @Valid @RequestBody CreateCompanyRequest createCompanyRequest,
            @AuthenticationPrincipal UserDetailImpl userDetails) {
        Long userId = userDetails.userId();
        String role = userDetails.role();
        createCompanyService.createCompany(createCompanyRequest, userId, role);
        return ApiResponse.success();
    }

    // 업체 전체 조회
    @GetMapping
    public ApiResponse<List<CompanyListResponse>> getListCompanies() {
        List<CompanyListResponse> companies = companyService.getListCompanies();
        return ApiResponse.success(companies);
    }

    // 업체 상세 조회
    @GetMapping("/{id}")
    public ApiResponse<CompanyDetailResponse> getDetailCompany(@PathVariable("id") UUID id) {
        CompanyDetailResponse company = companyService.getDetailCompany(id);
        return ApiResponse.success(company);
    }

    // 업체 수정
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER','COMPANY_MANAGER')")
    public ApiResponse<Void> updateCompany(@PathVariable("id") UUID id,
                                           @RequestBody UpdateCompanyRequest updateCompanyRequest,
                                           @AuthenticationPrincipal UserDetailImpl userDetails) {
        Long userId = userDetails.userId();
        String role = userDetails.role();
        updateCompanyService.updateCompany(id, updateCompanyRequest, userId, role);
        return ApiResponse.success();
    }

    // 업체 삭제
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('MASTER', 'HUB_MANAGER')")
    public ApiResponse<Void> deleteCompany(@PathVariable("id") UUID id,
                                           @AuthenticationPrincipal UserDetailImpl userDetails) {
        Long userId = userDetails.userId();
        String role = userDetails.role();
        companyService.deleteCompany(id, userId, role);
        return ApiResponse.success();
    }
}
