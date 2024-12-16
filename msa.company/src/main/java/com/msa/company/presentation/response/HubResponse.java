package com.msa.company.presentation.response;

public record HubResponse(
        String hubId,
        String city,
        Long managerId
) {
}
