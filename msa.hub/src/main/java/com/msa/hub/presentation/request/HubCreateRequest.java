package com.msa.hub.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HubCreateRequest(
        @NotBlank String name,
        @NotBlank String city,
        @NotBlank String district,
        @NotBlank String streetName,
        @NotBlank String streetNumber,
        @NotBlank String addressDetail,
        @NotNull Double latitude,
        @NotNull Double longitude
) {
}
