package com.msa.order.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.order.application.client.UserManager;
import com.msa.order.application.client.dto.UserData;
import com.msa.order.config.UserDetailImpl;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.entity.enums.UserRole;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.BusinessException.UnauthorizedException;
import com.msa.order.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
public class ReadOrderService {

	private final OrderRepository orderRepository;
	private final UserManager userManager;

	public ReadOrderService(OrderRepository orderRepository,
		@Qualifier("userClient") UserManager userManager) {
		this.orderRepository = orderRepository;
		this.userManager = userManager;
	}

	public Order getOrder(UUID orderId, UserDetailImpl userDetail) {
		Long userId = Long.parseLong(userDetail.userId());
		UserRole role = userDetail.getUserRole();
		UserData userData = getUserData(userDetail);

		Order order = findOrder(orderId);

		switch (role) {
			case MASTER -> {
				return order;
			}
			case HUB_MANAGER -> {
				if (!order.getDepartureHubId().equals(userData.hubId())) {
					throw new UnauthorizedException();
				}
				return order;
			}
			case DELIVERY_MANAGER -> {
				if (userData.type().equals("COMPANY") && !order.getDeliveryId().equals(userId)) {
					throw new UnauthorizedException();
				}
				return order;
			}
			case COMPANY_MANAGER -> {
				if (!order.getReceiverCompanyId().equals(userData.companyId())) {
					throw new UnauthorizedException();
				}
				return order;
			}
		}

		return order;
	}

	public Page<Order> getOrders(Pageable pageable, String search, UserDetailImpl userDetail) {
		Long userId = Long.parseLong(userDetail.userId());
		UserRole role = userDetail.getUserRole();
		UserData userData = getUserData(userDetail);

		switch (role) {
			case MASTER -> {
				return orderRepository.searchOrders(pageable, search);
			}
			case HUB_MANAGER -> {
				return orderRepository.searchOrdersByDepartureHubId(pageable, search, userData.hubId());
			}
			case DELIVERY_MANAGER -> {
				if ("COMPANY".equals(userData.type())) {
					return orderRepository.searchOrdersByDeliveryId(pageable, search, userId);
				} else {
					return orderRepository.searchOrders(pageable, search); // 허브 배송 담당자는 담당 허브가 없기 때문에 전부 조회함.
				}
			}
			case COMPANY_MANAGER -> {
				return orderRepository.searchOrdersByReceiveCompanyId(pageable, search, userData.companyId());
			}

			default -> throw new UnauthorizedException();
		}

	}

	private Order findOrder(UUID orderId) {
		return orderRepository.findByIdAndIsDeletedFalse(orderId)
			.orElseThrow(() -> new OrderException(ErrorCode.ORDER_NOT_FOUND));
	}

	private UserData getUserData(UserDetailImpl userDetail) {
		// TODO user 서비스 연동 필요
		// UserData userData = userManager.getUserInfo(Long.parseLong(userDetail.userId()));
		// if (userData.id() == null) {
		// 	throw new FeignException();
		// }
		// return userData;
		return new UserData(1L, "test user", "test@mail.com", "testslackId", UserRole.COMPANY_MANAGER, null, UUID.randomUUID(),
			UUID.randomUUID());
	}
}