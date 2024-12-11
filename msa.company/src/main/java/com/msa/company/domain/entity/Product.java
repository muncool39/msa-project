package com.msa.company.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "p_product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Long stock;

    // 품절 상태 확인
    @Column(nullable = false)
    private Boolean isOutOfStock = false;

    // 재고 변경 시 품절 상태도 자동으로 업데이트
    public void setStock(Long stock) {
        this.stock = stock;
        this.isOutOfStock = this.stock == 0;
    }

    public static Product create(Company company, String name, Long stock) {
        if (stock <= 0) {
            throw new IllegalArgumentException("재고량은 0보다 커야 합니다.");
        }

        return Product.builder()
                .company(company)
                .name(name)
                .stock(stock)
                .build();
    }
}
