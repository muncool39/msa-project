package com.msa.user.shipper.application.dto;

import com.msa.user.shipper.domain.model.Shipper;
import java.util.UUID;

public record ShipperResponse(
        UUID id,
        String hubId,
        String type,
        Integer deliveryOrder
) {

    public static ShipperResponse fromEntity(Shipper shipper){
        return new ShipperResponse(
                shipper.getId(),
                shipper.getHubId(),
                shipper.getType().toString(),
                shipper.getDeliveryOrder()
        );
    }
}
