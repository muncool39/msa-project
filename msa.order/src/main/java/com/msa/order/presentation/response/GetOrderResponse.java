package com.msa.order.presentation.response;

import com.msa.order.application.service.dto.OrderDetails;
import com.msa.order.domain.entity.Address;
import com.msa.order.domain.entity.enums.OrderStatus;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetOrderResponse(
    UUID orderId,
    UUID supplierCompanyId,
    String supplierCompanyName,
    UUID receiverCompanyId,
    String receiverCompanyName,
    Address receiverAddress,
    OrderStatus status,
    UUID itemId,
    String itemName,
    int quantity,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime canceledAt,
    UUID deliveryId
) {

  public static GetOrderResponse from(OrderDetails details) {

    return new GetOrderResponse(
        details.order().getId(),
        details.order().getSupplierCompanyId(),
        details.supplierCompanyName(),
        details.order().getReceiverCompanyId(),
        details.receiverCompanyName(),
        details.address(),
        details.order().getStatus(),
        details.order().getItemId(),
        details.order().getItemName(),
        details.order().getQuantity(),
        details.order().getDescription(),
        details.order().getCreatedAt(),
        details.order().getUpdatedAt(),
        details.order().getCanceledAt(),
        details.order().getDeliveryId()
    );
  }

}
