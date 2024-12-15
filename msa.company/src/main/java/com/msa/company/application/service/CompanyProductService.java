package com.msa.company.application.service;

import static com.msa.company.exception.ErrorCode.COMPANY_NOT_FOUND;

import com.msa.company.domain.entity.Company;
import com.msa.company.domain.entity.Product;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.request.CreateProductRequest;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyProductService {

    private final CompanyRepository companyRepository;
    private final ProductRepository productRepository;
    private final HubClient hubClient;

    // 상품 생성
    @Transactional
    public void createProduct(UUID companyId, CreateProductRequest productRequest,
                              Long userId, String role) {
        // 1. 회사 존재 여부 확인
        Company company = getCompany(companyId);

        /* TODO 1. HUB_MANAGER: 본인이 관리하는 허브인지 확인
        if ("HUB_MANAGER".equals(role)) {
            UUID hubId = company.getHubId();
        } else if ("COMPANY_MANAGER".equals(role)) {
           TODO 2. COMPANY_MANAGER: 본인이 관리하는 업체인지 확인
        }*/

        Product product = Product.create(
                productRequest,
                company,
                userId
        );
        productRepository.save(product);
    }

    // 업체 존재 여부 확인
    private Company getCompany(UUID companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));
    }
}
