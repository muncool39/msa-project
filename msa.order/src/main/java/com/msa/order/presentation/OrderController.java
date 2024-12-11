package com.msa.order.presentation;

import com.msa.order.application.annotation.RoleCheck;
import com.msa.order.application.service.CreateOrderService;
import com.msa.order.application.service.GetOrderService;
import com.msa.order.application.service.UpdateOrderService;
import com.msa.order.application.service.dto.OrderDetails;
import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.request.UpdateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;
import com.msa.order.presentation.response.GetOrderResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

  private final CreateOrderService createOrderService;
  private final GetOrderService getOrderService;
  private final UpdateOrderService updateOrderService;

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

  @RoleCheck(roles = {"MASTER", "HUB_MANAGER"})
  @PatchMapping("/{id}")
  public ApiResponse<Void> updateOrder(@PathVariable(name = "id") UUID orderId,
      @Valid @RequestBody UpdateOrderRequest request) {
    updateOrderService.updateOrder(orderId, request);
    return ApiResponse.success();
  }
}
