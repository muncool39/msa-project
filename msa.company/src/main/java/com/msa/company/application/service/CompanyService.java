package com.msa.company.application.service;

import com.msa.company.domain.entity.Address;
import com.msa.company.domain.entity.Company;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.request.CreateCompanyRequest;
import com.msa.company.presentation.request.UpdateCompanyRequest;
import com.msa.company.presentation.response.CompanyDetailResponse;
import com.msa.company.presentation.response.CompanyListResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final HubClient hubClient;
    private final CompanyRepository companyRepository;

    // 업체 생성
    @Transactional
    public void createCompany(CreateCompanyRequest createCompanyRequest, String userId, String role) {

        // 사업자 번호 중복 검사
        if (companyRepository.existsByBusinessNumber(createCompanyRequest.businessNumber())) {
            throw new CompanyException(ErrorCode.DUPLICATE_BUSINESS_NUMBER);
        }

        // TODO: HubId 존재 여부 확인 - 추후 확인 필요
        UUID hubId = createCompanyRequest.hubId();
        if ("MASTER".equals(role)) {
            if (!hubClient.isHubExists(hubId)) {
                throw new CompanyException(ErrorCode.HUB_NOT_FOUND);
            }
        }

        // TODO: HUB_MANAGER의 경우 소속 hubId를 가져오기 (추후 개발)

        Company company = Company.create(
                Long.valueOf(userId),
                createCompanyRequest.hubId(),
                createCompanyRequest.name(),
                createCompanyRequest.businessNumber(),
                createCompanyRequest.type(),
                createCompanyRequest.address().toEntity()
        );
        companyRepository.save(company);
    }

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

    // 업체 수정
    @Transactional
    public void updateCompany(UUID id, UpdateCompanyRequest request, Long userId, String role) {
        Company company = getCompanyAndCheckDeletion(id);

        // TODO: 허브 관리자(담당 허브), 업체 담당자(본인 업체)만 수정되도록 추후 개발

        if (request.userId() != null) {
            company.setUserId(request.userId());
        }
        if (request.name() != null) {
            company.setName(request.name());
        }
        if (request.address() != null) {
            Address address = request.address().toEntity();
            company.setAddress(address);
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

    // 업체 삭제
    @Transactional
    public void deleteCompany(UUID id, Long userId, String role) {

        // TODO: 마스터(전체), 허브 관리자(본인 허브만) 추후 개발

        Company company = getCompanyAndCheckDeletion(id);
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
}
