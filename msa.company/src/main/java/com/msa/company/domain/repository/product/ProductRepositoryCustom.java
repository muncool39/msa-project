package com.msa.company.domain.repository.product;

import com.msa.company.domain.model.Product;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getListProducts(String companyId, String companyName, String name, String isOutOfStock, Pageable pageable);

    Page<Product> findByCompanyId(UUID companyId, String name, String isOutOfStock, Pageable pageable);
}
