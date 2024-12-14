package com.msa.company.presentation.request;

import com.msa.company.domain.entity.enums.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateCompanyRequest(
    @NotNull(message = "대표 사용자 아이디를 필수로 입력해주세요.")
    Long userId,

    @NotBlank(message = "회사 이름을 필수로 입력해주세요.")
    String name,

    @NotBlank(message = "사업자 번호를 필수로 입력해주세요.")
    String businessNumber,

    @NotNull(message = "허브 아이디를 필수로 입력해주세요.")
    UUID hubId,

    @NotNull(message = "주소 정보를 필수로 입력해주세요.")
    AddressRequest address,

    @NotNull(message = "생산업체/수령업체 중 필수로 골라주세요.")
    CompanyType type
) {
}