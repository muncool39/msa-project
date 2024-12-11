package com.msa.company.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Embeddable
public class Address {
    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String district;

    @Column(nullable = false, length = 100)
    private String streetName;

    @Column(nullable = false, length = 100)
    private String streetNumber;

    @Column(length = 100)
    private String addressDetail;

    public static Address createAddress(String city, String district, String streetName, String streetNumber, String addressDetail) {
        return Address.builder()
                .city(city.trim())
                .district(district.trim())
                .streetName(streetName.trim())
                .streetNumber(streetNumber.trim())
                .addressDetail(addressDetail != null? addressDetail.trim() : null)
                .build();
    }
}
