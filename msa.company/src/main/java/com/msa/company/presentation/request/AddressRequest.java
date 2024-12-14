package com.msa.company.presentation.request;

import com.msa.company.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "도시를 필수로 입력해주세요.")
        String city,

        @NotBlank(message = "구/군 정보를 필수로 입력해주세요.")
        String district,

        @NotBlank(message = "도로명 주소를 필수로 입력해주세요.")
        String streetName,

        @NotBlank(message = "건물 번호를 필수로 입력해주세요.")
        String streetNumber,

        String addressDetail
) {
    public Address toEntity() {
        return new Address(city, district, streetName, streetNumber, addressDetail);
    }
}
