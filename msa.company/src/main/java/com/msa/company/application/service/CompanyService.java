package com.msa.company.application.service;

import com.msa.company.domain.model.Company;
import com.msa.company.domain.repository.company.CompanyRepository;
import com.msa.company.application.exception.CompanyException;
import com.msa.company.application.exception.ErrorCode;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.CompanyDetailResponse;
import com.msa.company.application.dto.response.CompanyListResponse;
import com.msa.company.application.dto.response.UserResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserClient userClient;

    // 업체 전체 조회
    @Transactional
    public Page<CompanyListResponse> getListCompanies(String hubId, String name, String type, String status, String address, Pageable pageable) {
        Page<Company> companies = companyRepository.getListCompanies(hubId, name, type, status, address, pageable);

        return companies.map(CompanyListResponse::from);
    }

    // 업체 상세 조회
    @Transactional
    public CompanyDetailResponse getDetailCompany(UUID id) {
        Company company = getCompanyAndCheckDeletion(id);
        return CompanyDetailResponse.from(company);
    }

    // 업체 삭제
    @Transactional
    public void deleteCompany(UUID id, Long userId, String role) {
        Company company = getCompanyAndCheckDeletion(id);

        if ("HUB_MANAGER".equals(role)) {
            validateHubManagerAccess(company, userId);
        }

        company.setIsDeleted(userId);
    }

    // 확인 메서드 ------------------------------------------------------------------

    // 업체 존재와 삭제 여부 확인
    private Company getCompanyAndCheckDeletion(UUID companyId) {
        // 회사 조회
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(ErrorCode.COMPANY_NOT_FOUND));

        // 삭제 여부 확인
        if (company.getIsDeleted()) {
            throw new CompanyException(ErrorCode.DELETED_COMPANY);
        }
        return company;
    }

    // HUB_MANAGER - 허브 아이디 비교
    private void validateHubManagerAccess(Company company, Long userId) {
        String companyHubId = String.valueOf(company.getHubId());
        String userHubId = getHubIdByUserId(userId);

        if (userHubId == null) {
            throw new CompanyException(ErrorCode.USER_NOT_ASSIGNED_TO_HUB);
        }

        if (!companyHubId.equals(userHubId)) {
            throw new CompanyException(ErrorCode.UNAUTHORIZED_ACCESS_HUB);
        }
    }

    // HUB_MANAGER - 허브 ID 가져오기
    private String getHubIdByUserId(Long userId) {
        ApiResponse<UserResponse> response = userClient.findUser(userId);
        return response.data().hubId();
    }
}
