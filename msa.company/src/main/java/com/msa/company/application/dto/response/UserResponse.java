package com.msa.company.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
        @JsonProperty("id") Long userId,
        String role,
        @JsonProperty("belongHubId") String hubId,
        @JsonProperty("belongCompanyId") String companyId
) {
}
