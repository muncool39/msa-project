package com.msa.order.application;

import com.msa.order.application.service.DeliveryManager;
import com.msa.order.application.service.ProductManager;
import com.msa.order.application.service.dto.CompanyData;
import com.msa.order.application.service.dto.DeliveryData;
import com.msa.order.application.service.dto.OrderDetails;
import com.msa.order.domain.entity.Address;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.ErrorCode;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetOrderService {

  private final OrderRepository orderRepository;
  private final ProductManager productManager;
  private final DeliveryManager deliveryManager;

  public GetOrderService(OrderRepository orderRepository,
      @Qualifier("productClient") ProductManager productManager,
      @Qualifier("deliveryClient") DeliveryManager deliveryManager) {
    this.orderRepository = orderRepository;
    this.productManager = productManager;
    this.deliveryManager = deliveryManager;
  }

  public OrderDetails getOrder(UUID orderId) {
    Order order = findOrder(orderId);
    // TODO companyService 연동 및 테스트 필요
//    CompanyData supplierCompany = productManager.getCompanyInfo(order.getSupplierCompanyId());
//    CompanyData receiveCompany = productManager.getCompanyInfo(order.getReceiverCompanyId());
    String supplierCompanyName = "농심";
    String receiveCompanyName = "삼다수";

    Address address = getAddress(orderId);

    //return OrderDetails.of(order, supplierCompany.name(), receiveCompany.name(), address);
    return OrderDetails.of(order, supplierCompanyName, receiveCompanyName, address);
  }

  private Address getAddress(UUID orderId) {
    // TODO deliveryService 연동 및 테스트 필요
//    DeliveryData deliveryData = deliveryManager.getDeliveryInfo(orderId);
//
//    return Address.of(deliveryData.city(), deliveryData.district(),
//        deliveryData.streetName(), deliveryData.streetNum(), deliveryData.detail());

    return Address.of("서울", "종로구", "광화문로", "35", "D타워 3층");
  }

  private Order findOrder(UUID orderId) {
    return orderRepository.findByIdAndIsDeletedFalse(orderId)
        .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
  }

}
