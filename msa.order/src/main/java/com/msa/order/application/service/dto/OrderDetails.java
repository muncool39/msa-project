package com.msa.order.application.service.dto;

import com.msa.order.domain.entity.Address;
import com.msa.order.domain.entity.Order;


public record OrderDetails(
    Order order,
    String supplierCompanyName,
    String receiverCompanyName,
    Address address) {

  public static OrderDetails of(Order order, String supplierCompanyName,
      String receiverCompanyName, Address address) {

    return new OrderDetails(order, supplierCompanyName, receiverCompanyName, address);
  }
}
