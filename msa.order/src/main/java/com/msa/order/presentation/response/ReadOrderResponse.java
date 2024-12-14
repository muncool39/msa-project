package com.msa.order.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.msa.order.domain.entity.Address;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.entity.enums.OrderStatus;

public record ReadOrderResponse(
    UUID orderId,
    UUID supplierCompanyId,
    UUID receiverCompanyId,
    Address receiverAddress,
    OrderStatus status,
    UUID itemId,
    String itemName,
    int quantity,
    String description,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime canceledAt,
    Long deliveryId
) {

  public static ReadOrderResponse from(Order order) {

    return new ReadOrderResponse(
        order.getId(),
        order.getSupplierCompanyId(),
        order.getReceiverCompanyId(),
        order.getAddress(),
        order.getStatus(),
        order.getItemId(),
        order.getItemName(),
        order.getQuantity(),
        order.getDescription(),
        order.getCreatedAt(),
        order.getUpdatedAt(),
        order.getCanceledAt(),
        order.getDeliveryId()
    );
  }

  public static PageResponse<ReadOrderResponse> pageOf(Page<Order> pagingData) {
    Page<ReadOrderResponse> responses = pagingData.map(ReadOrderResponse::from);
    return PageResponse.of(responses);
  }
}
