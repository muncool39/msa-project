package com.msa.order.presentation;

import com.msa.order.application.service.GetOrderService;
import com.msa.order.application.service.CreateOrderService;
import com.msa.order.application.service.dto.OrderDetails;
import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;
import com.msa.order.presentation.response.GetOrderResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final CreateOrderService createOrderService;
  private final GetOrderService getOrderService;

  @PostMapping
  public ApiResponse<Void> createOrder(@Valid @RequestBody CreateOrderRequest request) {
    createOrderService.createOrder(request);
    return ApiResponse.success();
  }

  @GetMapping("/{id}")
  public ApiResponse<GetOrderResponse> getOrder(@PathVariable(name = "id") UUID orderId) {
    OrderDetails orderDetails = getOrderService.getOrder(orderId);
    return ApiResponse.success(GetOrderResponse.from(orderDetails));
  }
}
