package com.msa.company.application.service;

import com.msa.company.domain.entity.Product;
import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.request.UpdateProductRequest;
import com.msa.company.presentation.response.ProductDetailResponse;
import com.msa.company.presentation.response.ProductListResponse;
import com.msa.company.presentation.response.StockResponse;
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
        Product product = getProductAndCheckDeletion(id);
        return ProductDetailResponse.from(product);
    }

    // 상품 수정
    @Transactional
    public void updateProduct(UUID id, UpdateProductRequest request,
                              Long userId, String role) {
        Product product = getProductAndCheckDeletion(id);

        if (request.name() != null) {
            product.setName(request.name());
        }
        if (request.stock() != null) {
            product.setStock(request.stock());
        }
    }

    // 상품 삭제
    @Transactional
    public void deleteProduct(UUID id, Long userId, String role) {
        Product product = getProductAndCheckDeletion(id);
        product.setIsDeleted(userId);
    }

    // 상품 재고 감소
    @Transactional
    public StockResponse decreaseStock(UUID id, Long stock) {
        Product product = getProductAndCheckDeletion(id);

        if (product.getStock() < stock) {
            throw new CompanyException(ErrorCode.PRODUCT_OUT_OF_STOCK);
        }

        product.setStock(product.getStock() - stock);
        productRepository.save(product);
        return StockResponse.from(product);
    }

    // 상품 재고 복원
    @Transactional
    public StockResponse restoreStock(UUID id, Long stock) {
        Product product = getProductAndCheckDeletion(id);
        product.setStock(product.getStock() + stock);
        productRepository.save(product);
        return StockResponse.from(product);
    }

    // 확인 메서드 ------------------------------------------------------------------

    // 상품 존재 + 삭제 여부 확인
    private Product getProductAndCheckDeletion(UUID id) {
        // 상품 조회
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CompanyException(ErrorCode.PRODUCT_NOT_FOUND));

        // 삭제 여부 확인
        if (product.getIsDeleted()) {
            throw new CompanyException(ErrorCode.DELETED_PRODUCT);
        }

        return product;
    }
}
