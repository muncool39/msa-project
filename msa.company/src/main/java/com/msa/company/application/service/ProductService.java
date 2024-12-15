package com.msa.company.application.service;

import static com.msa.company.exception.ErrorCode.PRODUCT_OUT_OF_STOCK;

import com.msa.company.domain.entity.Product;
import com.msa.company.domain.repository.ProductRepository;
import com.msa.company.exception.CompanyException;
import com.msa.company.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.presentation.request.UpdateProductRequest;
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

    // 상품 수정
    @Transactional
    public void updateProduct(UUID id, UpdateProductRequest request,
                              Long userId, String role) {
        Product product = getProduct(id);

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
        Product product = getProduct(id);
        product.setIsDeleted(userId);
    }

    // 상품 재고 감소
    @Transactional
    public void decreaseStock(UUID id, Long stock) {
        Product product = getProduct(id);
        if (product.getStock() < stock) {
            throw new CompanyException(PRODUCT_OUT_OF_STOCK);
        }
        product.setStock(product.getStock() - stock);
        productRepository.save(product);
    }

    // 상품 재고 복원
    @Transactional
    public void restoreStock(UUID id, Long stock) {
        Product product = getProduct(id);
        product.setStock(product.getStock() + stock);
        productRepository.save(product);
    }

    // 상품 확인
    private Product getProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new CompanyException(ErrorCode.PRODUCT_NOT_FOUND));
    }
}
