package com.msa.company.application.service;

import static com.msa.company.exception.ErrorCode.COMPANY_NOT_FOUND;

import com.msa.company.domain.entity.Company;
import com.msa.company.domain.entity.Product;
import com.msa.company.domain.repository.CompanyRepository;
import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.request.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CompanyRepository companyRepository;
    private final HubClient hubClient;


    @Transactional
    public void createProduct(ProductRequest productRequest, Long userId, String role) {

        // 업체 존재 여부 확인
        Company company = companyRepository.findById(productRequest.companyId())
                .orElseThrow(() -> new CompanyException(COMPANY_NOT_FOUND));

        /* TODO 1. HUB_MANAGER: 본인 허브만 생성 가능
        if ("HUB_MANAGER".equals(role)) {
            UUID hubId = company.getHubId();
        } else if ("COMPANY_MANAGER".equals(role)) {
           TODO 2. COMPANY_MANAGER: 본인 업체만 생성 가능
        }*/

        Product product = Product.create(
                productRequest,
                company,
                userId
        );
        productRepository.save(product);
    }
}
