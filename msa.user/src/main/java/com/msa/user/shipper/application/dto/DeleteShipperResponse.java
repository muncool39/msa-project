package com.msa.user.shipper.application.dto;

import com.msa.user.shipper.domain.model.Shipper;
import java.time.LocalDateTime;
import java.util.UUID;

public record DeleteShipperResponse(
        Long shipperId,
        LocalDateTime deletedAt
) {
    public static DeleteShipperResponse fromEntity(Shipper shipper) {
        return new DeleteShipperResponse(
                shipper.getId(),
                shipper.getDeletedAt()
        );
    }
}
