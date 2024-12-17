package com.msa.user.application.dto;

import com.msa.user.domain.model.Shipper;
import java.time.LocalDateTime;

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
