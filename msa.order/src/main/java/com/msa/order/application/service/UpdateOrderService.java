package com.msa.order.application.service;

import com.msa.order.application.service.dto.ProductStockData;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.ErrorCode;
import com.msa.order.presentation.request.UpdateOrderRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UpdateOrderService {

  private final OrderRepository orderRepository;
  private final ProductManager productManager;
  private final DeliveryManager deliveryManager;

  private final String HUB_WAITING ="HUB_WAITING";


  public UpdateOrderService(OrderRepository orderRepository,
      @Qualifier("productClient") ProductManager productManager,
      @Qualifier("deliveryClient") DeliveryManager deliveryManager) {
    this.orderRepository = orderRepository;
    this.productManager = productManager;
    this.deliveryManager = deliveryManager;
  }

  // TODO 유저,상품,배송 서비스 연동 전 임시 하드 코딩
  final UUID itemId = UUID.randomUUID();
  final UUID hubId = UUID.randomUUID();

  @Transactional
  public void updateOrder(UUID orderId, UpdateOrderRequest request) {
    // 상품 아이디 검증 후, 재고 감소 처리
    ProductStockData productStockData = reduceProductStock(request);

    // 배송 상태 확인 : 배송 준비중인 상태에서만 처리 가능
    checkDeliveryStatus(orderId);

    // 주문 업데이트 
    Order savedOrder = findOrder(orderId);
    savedOrder.updateItemInfo(request.itemId(), request.quantity());
  }

  private void checkDeliveryStatus(UUID orderId) {
    // Todo 배송 서비스 연동 테스트 필요
//    DeliveryData deliveryData = deliveryManager.getDeliveryInfo(orderId);
//    if (!HUB_WAITING.equals(deliveryData.status())) {
//      throw new OrderException(ErrorCode.ORDER_CHANGE_NOT_ALLOWED);
//    }
  }

  private Order findOrder(UUID orderId) {
    return orderRepository.findByIdAndIsDeletedFalse(orderId)
        .orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
  }

  private ProductStockData reduceProductStock(UpdateOrderRequest request) {
    // Todo 상품 서비스 연동 테스트 필요
//    ProductStockData stockData = productManager.reduceStock(request.itemId(),
//        new ProductStockRequest(request.quantity()));
//
//    if (stockData.id() == null) {
//      throw new OrderException(ErrorCode.STOCK_REDUCTION_FAILED);
//    }
//
//    return stockData;
    return new ProductStockData(itemId, hubId);
  }
}
