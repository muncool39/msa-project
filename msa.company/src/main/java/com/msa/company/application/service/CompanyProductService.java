package com.msa.company.application.service;

import com.msa.company.domain.entity.Company;
import com.msa.company.domain.entity.Product;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.presentation.request.CreateProductRequest;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.HubResponse;
import com.msa.company.presentation.response.ProductListResponse;
import com.msa.company.presentation.response.UserResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyProductService {

    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final HubClient hubClient;
    private final UserClient userClient;

    // 상품 생성
    @Transactional
    public void createProduct(UUID companyId, CreateProductRequest productRequest,
                              Long userId, String role) {
        // 1. 회사 존재 여부 확인
        Company company = getCompanyAndCheckDeletion(companyId);

        // 2. 권한 확인
        checkCreatePermission(companyId, userId, role, company);

        Product product = Product.create(productRequest, company);
        productRepository.save(product);
    }

    // 업체 내 상품 조회
    @Transactional(readOnly = true)
    public List<ProductListResponse> getProductsByCompany(UUID companyId) {
        // 1. 업체 존재 여부 확인
        getCompanyAndCheckDeletion(companyId);

        List<Product> products = productRepository.findByCompany_Id(companyId);

        // 2. 업체에 상품이 없는 경우
        if (products.isEmpty()) {
            throw new CompanyException(ErrorCode.PRODUCT_NOT_FOUND_IN_COMPANY);
        }

        return products.stream()
                .map(ProductListResponse::from)
                .collect(Collectors.toList());
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

    // 권한별 확인 로직
    private void checkCreatePermission(UUID companyId, Long userId, String role, Company company) {
        // HUB_MANAGER - 본인이 관리하는 허브인지 확인
        if ("HUB_MANAGER".equals(role)) {
            ApiResponse<HubResponse> hubResponse = hubClient.findHub(company.getHubId().toString());
            if (!hubResponse.data().managerId().equals(userId)) {
                throw new CompanyException(ErrorCode.HUB_ACCESS_DENIED);
            }
        }// COMPANY_MANAGER - 본인이 관리하는 업체인지 확인
        else if ("COMPANY_MANAGER".equals(role)) {
            ApiResponse<UserResponse> userResponse = userClient.findUser(userId);
            if (userResponse == null || userResponse.data() == null
                    ||!userResponse.data().companyId().equals(companyId.toString())) {
                throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
            }
        }
    }
}
