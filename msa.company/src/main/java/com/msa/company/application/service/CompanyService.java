package com.msa.company.application.service;

import com.msa.company.domain.entity.Company;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.CompanyDetailResponse;
import com.msa.company.presentation.response.CompanyListResponse;
import com.msa.company.presentation.response.UserResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserClient userClient;

    // 업체 전체 조회
    @Transactional
    public List<CompanyListResponse> getListCompanies() {
        return companyRepository.findAll().stream()
                .map(CompanyListResponse::from)
                .collect(Collectors.toList());
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
