package com.msa.order.application.service.dto;

import java.util.UUID;

public record DeliveryData(
    UUID deliveryId,
    UUID orderId,
    String city,
    String district,
    String streetName,
    String streetNum,
    String detail
) {


}