package com.msa.company.application.service;

import com.msa.company.domain.entity.Address;
import com.msa.company.domain.entity.Company;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.presentation.request.UpdateCompanyRequest;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.UserResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCompanyService {

    private final CompanyRepository companyRepository;
    private final UserClient userClient;

    // 업체 수정
    @Transactional
    public void updateCompany(UUID id, UpdateCompanyRequest request, Long userId, String role) {
        Company company = getCompanyAndCheckDeletion(id);

        // MASTER: 모든 수정 가능
        if ("MASTER".equals(role)) {
            applyMasterUpdates(company, request);
        }
        // HUB_MANAGER: 본인 허브의 업체만, 특정 필드만 수정 가능
        else if ("HUB_MANAGER".equals(role)) {
            validateHubManagerAccess(company, userId);
            applyHubManagerUpdates(company, request);
        }
        // COMPANY_MANAGER: 본인 업체만, 특정 필드만 수정 가능
        else if ("COMPANY_MANAGER".equals(role)) {
            validateCompanyManagerAccess(company, userId);
            applyCompanyManagerUpdates(company, request);
        }
    }


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

    // MASTER: 모든 필드 수정 가능
    private void applyMasterUpdates(Company company, UpdateCompanyRequest request) {
        if (request.userId() != null) {
            company.setUserId(request.userId());
        }
        if (request.name() != null) {
            company.setName(request.name());
        }
        if (request.address() != null) {
            company.setAddress(request.address().toEntity());
        }
        if (request.hubId() != null) {
            company.setHubId(request.hubId());
        }
        if (request.status() != null) {
            company.setStatus(request.status());
        }
        if (request.type() != null) {
            company.setType(request.type());
        }
    }

    // 허브 관리자(HUB_MANAGER)의 권한 검증: 본인 허브의 업체만 접근 가능
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

    // COMPANY_MANAGER: 본인 업체인지 확인
    private void validateCompanyManagerAccess(Company company, Long userId) {
        if (!company.getUserId().equals(userId)) {
            throw new CompanyException(ErrorCode.UNAUTHORIZED_ACCESS_COMPANY);
        }
    }

    // HUB_MANAGER: 본인 허브의 업체만 수정, city 제외한 address 수정
    private void applyHubManagerUpdates(Company company, UpdateCompanyRequest request) {
        if (request.userId() != null) {
            company.setUserId(request.userId());
        }
        if (request.name() != null) {
            company.setName(request.name());
        }
        if (request.address() != null) {
            Address updatedAddress = request.address().toEntity();
            if (updatedAddress.getCity() != null) {
                updatedAddress.setCity(company.getAddress().getCity()); // 기존 city 유지
            }
            company.setAddress(updatedAddress);
        }
        if (request.status() != null) {
            company.setStatus(request.status());
        }
        if (request.type() != null) {
            company.setType(request.type());
        }
    }

    // COMPANY_MANAGER: 본인 업체만 수정, name과 type만 가능
    private void applyCompanyManagerUpdates(Company company, UpdateCompanyRequest request) {
        if (request.name() != null) {
            company.setName(request.name());
        }
        if (request.type() != null) {
            company.setType(request.type());
        }
    }

    // HUB_MANAGER - 허브 ID 가져오기
    private String getHubIdByUserId(Long userId) {
        ApiResponse<UserResponse> response = userClient.findUser(userId);
        return response.data().hubId();
    }
}
