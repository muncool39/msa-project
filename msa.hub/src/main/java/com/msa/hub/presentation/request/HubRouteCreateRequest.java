package com.msa.hub.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record HubRouteCreateRequest(
        @NotBlank String sourceHubId,
        @NotBlank String destinationHubId
) {
}
