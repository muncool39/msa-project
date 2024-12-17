package com.msa.company.presentation.request;

import com.msa.company.domain.model.enums.CompanyStatus;
import com.msa.company.domain.model.enums.CompanyType;
import java.util.UUID;
import lombok.Builder;

@Builder
public record UpdateCompanyRequest(

        Long userId,
        String name,
        AddressRequest address,
        UUID hubId,
        CompanyStatus status,
        CompanyType type

) {
}
