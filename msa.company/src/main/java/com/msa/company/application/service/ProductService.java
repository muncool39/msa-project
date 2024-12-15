package com.msa.company.application.service;

import com.msa.company.domain.entity.Product;
import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.response.ProductDetailResponse;
import com.msa.company.presentation.response.ProductListResponse;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final HubClient hubClient;

    // 상품 전체 조회
    @Transactional(readOnly = true)
    public List<ProductListResponse> getListProducts() {
        return productRepository.findAll().stream()
                .map(ProductListResponse::from)
                .collect(Collectors.toList());
    }

    // 상품 단건 조회
    @Transactional(readOnly = true)
    public ProductDetailResponse getDetailProduct(UUID id) {
        Product product = getProduct(id);
        return ProductDetailResponse.from(product);
    }

    // 상품 확인
    private Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CompanyException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}
