package com.msa.user.shipper.presentation.request;

import com.msa.user.domain.model.type.ShipperType;
import jakarta.validation.constraints.NotBlank;

public record UpdateShipperRequest(
        String hubId,
        @NotBlank
        ShipperType type
) {
}
