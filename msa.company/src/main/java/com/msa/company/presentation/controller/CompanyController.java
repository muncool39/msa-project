package com.msa.company.presentation.controller;

import com.msa.company.application.service.CompanyService;
import com.msa.company.application.service.CreateCompanyService;
import com.msa.company.application.service.UpdateCompanyService;
import com.msa.company.infrastructure.config.dto.UserDetailImpl;
import com.msa.company.presentation.request.CreateCompanyRequest;
import com.msa.company.presentation.request.UpdateCompanyRequest;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.CompanyDetailResponse;
import com.msa.company.application.dto.response.CompanyListResponse;
import com.msa.company.application.dto.response.PageResponse;
import jakarta.validation.Valid;
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
    public ApiResponse<PageResponse<CompanyListResponse>> getListCompanies(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false) String hubId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String address) {

        // Pageable 설정
        Pageable page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Order.desc(sortBy)));

        return ApiResponse.success(PageResponse.of(
                companyService.getListCompanies(hubId, name, type, status, address, page))
        );
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
