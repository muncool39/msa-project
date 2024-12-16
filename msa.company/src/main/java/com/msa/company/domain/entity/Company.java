package com.msa.company.domain.entity;

import com.msa.company.domain.entity.enums.CompanyStatus;
import com.msa.company.domain.entity.enums.CompanyType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Setter
@Table(name = "p_company")
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "hub_id", nullable = false)
    private UUID hubId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "business_number",nullable = false)
    private String businessNumber;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CompanyType type;

    @Enumerated(EnumType.STRING)
    private CompanyStatus status = CompanyStatus.PENDING;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    public static Company create(Long userId, UUID hubId, String name, String businessNumber, CompanyType type, Address address) {
        return Company.builder()
                .userId(userId)
                .hubId(hubId)
                .name(name)
                .businessNumber(businessNumber)
                .address(address)
                .type(type)
                .status(CompanyStatus.PENDING)
                .build();
    }
}
