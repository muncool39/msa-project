package com.msa.company.application.service;

import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.response.ProductListResponse;
import java.util.List;
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
}
