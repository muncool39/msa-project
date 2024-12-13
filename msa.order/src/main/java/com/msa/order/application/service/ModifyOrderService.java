package com.msa.order.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.order.application.client.DeliveryManager;
import com.msa.order.application.client.HubManager;
import com.msa.order.application.client.ProductManager;
import com.msa.order.application.client.dto.DeliveryData;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.entity.enums.UserRole;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.BusinessException.UnauthorizedException;
import com.msa.order.exception.ErrorCode;
import com.msa.order.presentation.request.UpdateOrderRequest;

@Service
@Transactional(readOnly = true)
public class ModifyOrderService {

	private final OrderRepository orderRepository;
	private final ProductManager productManager;
	private final DeliveryManager deliveryManager;
	private final HubManager hubManager;

	public ModifyOrderService(OrderRepository orderRepository,
		@Qualifier("productClient") ProductManager productManager,
		@Qualifier("deliveryClient") DeliveryManager deliveryManager,
		@Qualifier("hubClient") HubManager hubManager) {
		this.orderRepository = orderRepository;
		this.productManager = productManager;
		this.deliveryManager = deliveryManager;
		this.hubManager = hubManager;
	}

	// 임시 하드코딩
	final UUID tempHubId = UUID.randomUUID();
	final String tempDeliveryStatus = "HUB_WAITING";

	@Transactional
	public void updateOrder(UUID orderId, UpdateOrderRequest request, String userId, UserRole role) {
		DeliveryData deliveryData = getDeliveryData(orderId);
		//validateHubManagerAccess(userId, role, deliveryData.departureHubId());
		validateHubManagerAccess(userId, role, tempHubId);
		Order savedOrder = findOrder(orderId);
		//checkDeliveryStatus(savedOrder, deliveryData.status());
		checkDeliveryStatus(savedOrder, tempDeliveryStatus);
		reduceProductStock(request); // ?? 기존 주문에 있던거는 복원시키고 수정된거 감소해야하는거 아님?
		savedOrder.updateItemInfo(request.itemId(), request.quantity());
	}

	@Transactional
	public void cancelOrder(UUID orderId, String userId) {
		// TODO 권한 체크
		// 업체담당자 : 본인 주문만 취소 가능
		// 허브 관리자 : 담당 허브에 들어온 주문 내역만 취소 가능
		// 배송 담당자 : 본인이 배송할 주문내역만 취소 가능
		DeliveryData deliveryData = getDeliveryData(orderId);
		Order savedOrder = findOrder(orderId);
		//checkDeliveryStatus(savedOrder, deliveryData.status());
		checkDeliveryStatus(savedOrder, tempDeliveryStatus);
		restoreProductStock(savedOrder.getItemId(), savedOrder.getQuantity());
		savedOrder.cancelOrder(userId);
	}

	@Transactional
	public void deleteOrder(UUID orderId, String userId, UserRole role) {
		DeliveryData deliveryData = getDeliveryData(orderId);
		//validateHubManagerAccess(userId, role, deliveryData.departureHubId());
		validateHubManagerAccess(userId, role, tempHubId);
		Order savedOrder = findOrder(orderId);
		//checkDeliveryStatus(savedOrder, deliveryData.status());
		checkDeliveryStatus(savedOrder, tempDeliveryStatus);
		restoreProductStock(savedOrder.getItemId(), savedOrder.getQuantity());
		savedOrder.deleteOrder(userId);
	}

	private DeliveryData getDeliveryData(UUID orderId) {
		// Todo 배송 서비스 연동 및 테스트 필요
		// DeliveryData deliveryData = deliveryManager.getDeliveryInfo(orderId);
		// if (deliveryData.deliveryId() == null) {
		// 	throw new OrderException(ErrorCode.DELIVERY_NOT_FOUND);
		// }
		//
		// return deliveryData;
		return null;
	}

	private void validateHubManagerAccess(String userId, UserRole role, UUID departureHubId) {
		if (UserRole.HUB_MANAGER.equals(role)) {
			Long hubManagerId = getHubManagerId(departureHubId); // 해당 허브의 관리자 id

			if (!userId.equals(String.valueOf(hubManagerId))) {
				throw new UnauthorizedException();
			}
		}
	}

	private Long getHubManagerId(UUID hubId) {
		// Todo hub 서비스 연동 및 테스트 필요
		// HubData hubData = hubManager.getHubInfo(hubId);
		// if (hubData.managerId() == null) {
		// 	throw new OrderException(ErrorCode.HUB_NOT_FOUND);
		// }
		// return hubData.managerId();
		return 2L;
	}

	private Order findOrder(UUID orderId) {
		return orderRepository.findByIdAndIsDeletedFalse(orderId)
			.orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
	}

	private void checkDeliveryStatus(Order savedOrder, String status) {
		// Todo 배송 서비스 연동 테스트 필요
		savedOrder.validateChangeOrder(status);
	}

	private void reduceProductStock(UpdateOrderRequest request) {
		// Todo 상품 서비스 연동 테스트 필요
		// ProductStockData stockData = productManager.reduceStock(request.itemId(),
		// 	new ProductStockRequest(request.quantity()));
		//
		// if (stockData.id() == null) {
		// 	throw new OrderException(ErrorCode.STOCK_REDUCTION_FAILED);
		// }
	}

	private void restoreProductStock(UUID itemId, int quantity) {
		// Todo 상품 서비스 연동 테스트 필요
		// ProductStockData stockData = productManager.restoreStock(itemId, new ProductStockRequest(quantity));
		//
		// if (stockData.id() == null) {
		// 	throw new OrderException(ErrorCode.STOCK_RESTORE_FAILED);
		// }

	}

}
