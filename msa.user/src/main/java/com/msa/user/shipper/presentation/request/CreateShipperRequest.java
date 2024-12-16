package com.msa.user.shipper.presentation.request;

import com.msa.user.shipper.domain.model.type.ShipperType;
import jakarta.validation.constraints.NotBlank;

public record CreateShipperRequest(
        String hubId,
        @NotBlank
        ShipperType type
) {
}
