package com.msa.company.application.service;

import static com.msa.company.exception.ErrorCode.DUPLICATE_BUSINESS_NUMBER;
import static com.msa.company.exception.ErrorCode.HUB_NOT_FOUND;
import com.msa.company.domain.entity.Company;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.request.CreateCompanyRequest;
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
            throw new CompanyException(DUPLICATE_BUSINESS_NUMBER);
        }

        // TODO: HubId 존재 여부 확인 - 추후 확인 필요
        UUID hubId = createCompanyRequest.hubId();
        if ("MASTER".equals(role)) {
            if (!hubClient.isHubExists(hubId)) {
                System.out.println("Is hub exists: " + hubId);
                throw new CompanyException(HUB_NOT_FOUND);
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
}
