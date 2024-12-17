package com.msa.company.application.service;

import com.msa.company.domain.model.Company;
import com.msa.company.domain.model.Product;
import com.msa.company.domain.model.enums.CompanyStatus;
import com.msa.company.domain.repository.company.CompanyRepository;
import com.msa.company.domain.repository.product.ProductRepository;
import com.msa.company.application.exception.CompanyException;
import com.msa.company.application.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.presentation.request.CreateProductRequest;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.HubResponse;
import com.msa.company.application.dto.response.ProductListResponse;
import com.msa.company.application.dto.response.UserResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<ProductListResponse> getProductsByCompany(
            UUID companyId, String name, String isOutOfStock,  Pageable pageable) {
        // 1. 업체 존재 여부 확인
        getCompanyAndCheckDeletion(companyId);

        Page<Product> products = productRepository.findByCompanyId(companyId, name, isOutOfStock, pageable);

        // 2. 업체에 상품이 없는 경우
        if (products.isEmpty()) {
            throw new CompanyException(ErrorCode.PRODUCT_NOT_FOUND_IN_COMPANY);
        }

        return products.map(ProductListResponse::from);
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

        // 업체 상태 확인: APPROVED만 허용
        if (company.getStatus() != CompanyStatus.APPROVED) {
            throw new CompanyException(ErrorCode.COMPANY_NOT_APPROVED);
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
