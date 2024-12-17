package com.msa.user.shipper.application.dto;

import com.msa.user.shipper.domain.model.Shipper;
import java.util.UUID;

public record ShipperResponse(
        Long id,
        Long userId,
        String hubId,
        String type,
        Integer deliveryOrder
) {

    public static ShipperResponse fromEntity(Shipper shipper){
        return new ShipperResponse(
                shipper.getId(),
                shipper.getUserId(),
                shipper.getHubId(),
                shipper.getType().toString(),
                shipper.getDeliveryOrder()
        );
    }
}
