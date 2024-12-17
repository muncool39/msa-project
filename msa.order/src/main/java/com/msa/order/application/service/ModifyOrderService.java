package com.msa.order.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.order.application.client.DeliveryManager;
import com.msa.order.application.client.ProductManager;
import com.msa.order.application.client.UserManager;
import com.msa.order.application.client.dto.DeliveryData;
import com.msa.order.application.client.dto.ProductData;
import com.msa.order.application.client.dto.ProductStockRequest;
import com.msa.order.application.client.dto.UserData;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.entity.enums.UserRole;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.FeignException;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.BusinessException.OrderNotFoundException;
import com.msa.order.exception.BusinessException.ProductStockException;
import com.msa.order.exception.BusinessException.UnauthorizedException;
import com.msa.order.exception.ErrorCode;
import com.msa.order.presentation.request.UpdateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;

@Service
@Transactional(readOnly = true)
public class ModifyOrderService {

	private final OrderRepository orderRepository;
	private final ProductManager productManager;
	private final DeliveryManager deliveryManager;
	private final UserManager userManager;

	public ModifyOrderService(OrderRepository orderRepository,
		@Qualifier("productClient") ProductManager productManager,
		@Qualifier("deliveryClient") DeliveryManager deliveryManager,
		@Qualifier("userClient") UserManager userManager) {
		this.orderRepository = orderRepository;
		this.productManager = productManager;
		this.deliveryManager = deliveryManager;
		this.userManager = userManager;
	}

	@Transactional
	public void updateOrder(UUID orderId, UpdateOrderRequest request, String userId, UserRole role) {
		Order savedOrder = findOrder(orderId);
		UserData userData = getUserData(userId);
		validateUserAccess(role, savedOrder, userData);
		DeliveryData deliveryData = getDeliveryData(orderId);
		savedOrder.validateChangeOrder(deliveryData.status());
		changeProductStock(savedOrder, request);
		savedOrder.updateItemInfo(request.itemId(), request.quantity());
	}

	@Transactional
	public void cancelOrder(UUID orderId, String userId, UserRole role) {
		Order savedOrder = findOrder(orderId);
		UserData userData = getUserData(userId);
		validateUserAccess(role, savedOrder, userData);
		DeliveryData deliveryData = getDeliveryData(orderId);
		savedOrder.validateChangeOrder(deliveryData.status());
		restoreProductStock(savedOrder.getItemId(), savedOrder.getQuantity());
		savedOrder.cancelOrder(userId);
	}

	@Transactional
	public void deleteOrder(UUID orderId, String userId, UserRole role) {
		Order savedOrder = findOrder(orderId);
		UserData userData = getUserData(userId);
		validateUserAccess(role, savedOrder, userData);
		DeliveryData deliveryData = getDeliveryData(orderId);
		savedOrder.validateChangeOrder(deliveryData.status());
		restoreProductStock(savedOrder.getItemId(), savedOrder.getQuantity());
		savedOrder.deleteOrder(userId);
	}

	private Order findOrder(UUID orderId) {
		return orderRepository.findByIdAndIsDeletedFalse(orderId)
			.orElseThrow(OrderNotFoundException::new);
	}

	private UserData getUserData(String userId) {
		ApiResponse<UserData> response = userManager.getUserInfo(Long.parseLong(userId));
		UserData userData = response.data();
		if (userData.id() == null) {
			throw new FeignException(ErrorCode.USER_SERVICE_ERROR);
		}
		return userData;
	}

	private static void validateUserAccess(UserRole role, Order savedOrder, UserData userData) {
		switch (role) {
			case HUB_MANAGER -> {
				if (!savedOrder.getDepartureHubId().equals(userData.belongHubId())) {
					throw new UnauthorizedException();
				}
			}
			case COMPANY_MANAGER -> {
				if (!savedOrder.getReceiverCompanyId().equals(userData.belongCompanyId())) {
					throw new UnauthorizedException();
				}
			}
		}
	}

	private DeliveryData getDeliveryData(UUID orderId) {
		 DeliveryData deliveryData = deliveryManager.getDeliveryInfo(orderId);
		if (deliveryData.deliveryId() == null) {
			throw new OrderException(ErrorCode.DELIVERY_NOT_FOUND);
		}

		return deliveryData;
	}

	private void changeProductStock(Order savedOrder, UpdateOrderRequest request) {
		if (savedOrder.getItemId().equals(request.itemId())) {
			reduceProductStock(request.itemId(), request.quantity());
		} else {
			restoreProductStock(savedOrder.getItemId(), savedOrder.getQuantity());
			reduceProductStock(request.itemId(), request.quantity());
		}
	}

	private void reduceProductStock(UUID itemId, int quantity) {
		ApiResponse<ProductData> response = productManager.reduceStock(itemId, new ProductStockRequest(quantity));
		ProductData productData = response.data();

		if (productData.productId() == null) {
			throw new ProductStockException(ErrorCode.STOCK_REDUCTION_FAILED);
		}
	}

	private void restoreProductStock(UUID itemId, int quantity) {
		ApiResponse<ProductData> response = productManager.restoreStock(itemId, new ProductStockRequest(quantity));
		ProductData productData = response.data();

		if (productData.productId() == null) {
			throw new ProductStockException(ErrorCode.STOCK_RESTORE_FAILED);
		}

	}

}