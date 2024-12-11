package com.msa.order.domain.entity;

import java.util.UUID;

import com.msa.order.domain.entity.enums.OrderStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_order")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private UUID supplierCompanyId;

  @Column(nullable = false)
  private UUID receiverCompanyId;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(nullable = false)
  private UUID itemId;

  @Column(nullable = false)
  private String itemName;

  @Column(nullable = false)
  private int quantity;

  private String description;

  private UUID deliveryId;


  public static Order create(UUID supplierCompanyId, UUID receiverCompanyId, UUID itemId,
      String itemName, int quantity, String description) {

    return Order.builder()
        .supplierCompanyId(supplierCompanyId)
        .receiverCompanyId(receiverCompanyId)
        .status(OrderStatus.ORDER_REQUEST)
        .itemId(itemId)
        .itemName(itemName)
        .quantity(quantity)
        .description(description)
        .build();
  }

  public void updateDeliveryId(UUID deliveryId) {
    this.status = OrderStatus.ORDERED;
    this.deliveryId = deliveryId;
  }

}
