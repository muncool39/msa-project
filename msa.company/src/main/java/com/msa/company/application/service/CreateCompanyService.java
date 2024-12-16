package com.msa.company.application.service;

import com.msa.company.domain.entity.Address;
import com.msa.company.domain.entity.Company;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.presentation.request.CreateCompanyRequest;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.HubResponse;
import com.msa.company.presentation.response.UserResponse;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCompanyService {

    private final HubClient hubClient;
    private final UserClient userClient;
    private final CompanyRepository companyRepository;

    // 업체 생성
    @Transactional
    public void createCompany(CreateCompanyRequest createCompanyRequest, Long userId, String role) {

        // 사업자 번호 중복 검사
        checkBusinessNumber(createCompanyRequest.businessNumber());

        String hubId = getHubId(createCompanyRequest, userId, role);
        String city = getCityForHub(hubId);

        // 주소 설정
        Address address = createCompanyRequest.address().toEntity();
        address.setCity(city);

        Company company = Company.create(
                userId,
                UUID.fromString(hubId),
                createCompanyRequest.name(),
                createCompanyRequest.businessNumber(),
                createCompanyRequest.type(),
                address
        );

        companyRepository.save(company);
    }

    // 사업자 번호 중복 검사
    private void checkBusinessNumber(String businessNumber) {
        if (companyRepository.existsByBusinessNumber(businessNumber)) {
            throw new CompanyException(ErrorCode.DUPLICATE_BUSINESS_NUMBER);
        }
    }

    // 허브 아이디 얻기 (MASTER / HUB_MANAGER)
    private String getHubId(CreateCompanyRequest createCompanyRequest, Long userId, String role) {
        String hubId;

        if ("MASTER".equals(role)) {
            // hubId 입력 필수
            if (createCompanyRequest.hubId() == null) {
                throw new CompanyException(ErrorCode.HUB_ID_REQUIRED);
            }

            // hubId 존재 여부 확인
            ApiResponse<HubResponse> hubResponse = hubClient.findHub(createCompanyRequest.hubId().toString());
            if (hubResponse == null || hubResponse.data() == null) {
                throw new CompanyException(ErrorCode.HUB_NOT_FOUND);
            }
            hubId = createCompanyRequest.hubId().toString();

        } else {
            // HUB_MANAGER - userId로 hubId 존재 확인
            ApiResponse<UserResponse> userResponse = userClient.findUser(userId);
            System.out.println("User Client Response: " + userResponse);
            if (userResponse == null || userResponse.data() == null || userResponse.data().hubId() == null) {
                throw new CompanyException(ErrorCode.USER_NOT_ASSIGNED_TO_HUB);
            }
            hubId = userResponse.data().hubId();
        }
        return hubId;
    }

    // hubId를 바탕으로 city 가져오기
    private String getCityForHub(String hubId) {
        ApiResponse<HubResponse> hubResponse = hubClient.findHub(hubId);
        if (hubResponse == null || hubResponse.data() == null) {
            throw new CompanyException(ErrorCode.HUB_NOT_FOUND); // 유효하지 않은 hubId
        }
        return hubResponse.data().city();
    }
}
