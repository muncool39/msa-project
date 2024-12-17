package com.msa.company.application.service;

import com.msa.company.domain.model.Product;
import com.msa.company.domain.repository.product.ProductRepository;
import com.msa.company.application.exception.CompanyException;
import com.msa.company.application.exception.ErrorCode;
import com.msa.company.infrastructure.HubClient;
import com.msa.company.infrastructure.UserClient;
import com.msa.company.presentation.request.UpdateProductRequest;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.HubResponse;
import com.msa.company.application.dto.response.ProductDetailResponse;
import com.msa.company.application.dto.response.ProductListResponse;
import com.msa.company.application.dto.response.StockResponse;
import com.msa.company.application.dto.response.UserResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final HubClient hubClient;
    private final UserClient userClient;

    // 상품 전체 조회
    @Transactional(readOnly = true)
    public Page<ProductListResponse> getListProducts(
            String companyId, String companyName, String name, String isOutOfStock, Pageable pageable) {
        Page<Product> products = productRepository.getListProducts(companyId, companyName, name, isOutOfStock, pageable);

        return products.map(ProductListResponse::from);
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

        if ("HUB_MANAGER".equals(role)){
            checkHubManagerPermission(userId, product);
        }else if ("COMPANY_MANAGER".equals(role)) {
            checkCompanyManagerPermission(userId, product);
        }

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

        if ("HUB_MANAGER".equals(role)) {
            checkHubManagerPermission(userId, product);
        }
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

    // HUB_MANAGER 권한 확인
    private void checkHubManagerPermission(Long userId, Product product) {
        ApiResponse<HubResponse> hubResponse = hubClient.findHub(product.getHubId().toString());
        if (hubResponse == null || !hubResponse.data().managerId().equals(userId)) {
            throw new CompanyException(ErrorCode.HUB_ACCESS_DENIED);
        }
    }

    // Company_MANAGER 권한 확인
    private void checkCompanyManagerPermission(Long userId, Product product) {
        ApiResponse<UserResponse> userResponse = userClient.findUser(userId);
        if (userResponse == null || !userResponse.data().companyId().equals(product.getCompany().getId().toString())) {
            throw new CompanyException(ErrorCode.COMPANY_ACCESS_DENIED);
        }
    }
}
