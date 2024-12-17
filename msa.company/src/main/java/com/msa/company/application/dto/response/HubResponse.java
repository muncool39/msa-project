package com.msa.company.application.dto.response;

public record HubResponse(
        String hubId,
        String city,
        Long managerId
) {
}
