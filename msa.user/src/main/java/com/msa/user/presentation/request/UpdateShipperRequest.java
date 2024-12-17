package com.msa.user.presentation.request;

import com.msa.user.domain.model.ShipperType;
import jakarta.validation.constraints.NotBlank;

public record UpdateShipperRequest(
        String hubId,
        @NotBlank
        ShipperType type
) {
}
