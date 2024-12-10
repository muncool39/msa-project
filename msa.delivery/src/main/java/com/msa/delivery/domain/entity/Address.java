package com.msa.delivery.domain.entity;

import org.springframework.util.Assert;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class Address {

	@Column(nullable = false)
	private String city;

	@Column(nullable = false)
	private String district;

	@Column(nullable = false)
	private String streetName;

	@Column(nullable = false)
	private String streetNum;

	private String detail;


	public static Address of(String city, String district, String streetName, String streetNum, String detail) {

		return Address.builder()
			.city(city.trim())
			.district(district.trim())
			.streetName(streetName.trim())
			.streetNum(streetNum.trim())
			.detail(detail != null ? detail.trim() : null)
			.build();
	}
}