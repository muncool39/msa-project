package com.msa.delivery.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.UserData;
import com.msa.delivery.config.UserDetailImpl;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.enums.UserRole;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.exception.BusinessException.DeliveryNotFoundException;
import com.msa.delivery.exception.BusinessException.UnauthorizedException;
import com.msa.delivery.exception.ErrorCode;

@Service
@Transactional(readOnly = true)
public class ReadDeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final UserManager userManager;

	public ReadDeliveryService(DeliveryRepository deliveryRepository, 
		@Qualifier("userClient") UserManager userManager) {
		this.deliveryRepository = deliveryRepository;
		this.userManager = userManager;
	}

	public Delivery getDeliveryByOrderId(UUID orderId) {
		return deliveryRepository.findByOrderId(orderId)
			.orElseThrow(DeliveryNotFoundException::new);
	}

	public Delivery getDelivery(UUID deliveryId, UserDetailImpl userDetail) {
		Long userId = Long.parseLong(userDetail.userId());
		UserRole role = userDetail.getUserRole();
		UserData userData = getUserData(userDetail);

		Delivery delivery = findDelivery(deliveryId);

		switch (role) {
			case MASTER -> {
				return delivery;
			}
			case HUB_MANAGER -> {
				validateHubManagerAccess(userData, delivery);
				return delivery;
			}
			case DELIVERY_MANAGER -> {
				validateDeliveryManagerAccess(userData, delivery, userId);
				return delivery;
			}
			case COMPANY_MANAGER -> {
				if (!delivery.getDestinationHubId().equals(userData.hubId())) {
					throw new UnauthorizedException(ErrorCode.NOT_ALLOWED_COMPANY_MANAGER);
				}
				return delivery;
			}
		}

		return delivery;
	}

	public Page<Delivery> getDeliveries(Pageable pageable, String search, UserDetailImpl userDetail) {
		Long userId = Long.parseLong(userDetail.userId());
		UserRole role = userDetail.getUserRole();
		UserData userData = getUserData(userDetail);

		switch (role) {
			case MASTER -> {
				return deliveryRepository.searchDeliveries(pageable, search);
			}
			case HUB_MANAGER -> {
				// 담당 허브내의 배송 내역만 조회할 수 잇음.
				// 출발허드 도착허브 아무거나 일치하면
				return deliveryRepository.searchDeliveriesByHubId(pageable, search, userData.hubId());
			}
			case DELIVERY_MANAGER -> {
				// 담당할 배송 내역만 조회할 수 있음.
				if ("HUB".equals(userData.type())) {
					return deliveryRepository.searchDeliveriesByHubDeliverId(pageable, search, userId);
				} else {
					return deliveryRepository.searchDeliveriesByCompanyDeliveryId(pageable, search, userId);
				}
			}
			case COMPANY_MANAGER -> {
				// 본인의 주문에 관한 배송내역만 조회할 수 있음
				return deliveryRepository.searchDeliveriesByReceiveCompanyId(pageable, search, userData.companyId());
			}
		}

		return null;
	}

	private static void validateDeliveryManagerAccess(UserData userData, Delivery delivery, Long userId) {
		if (userData.type().equals("HUB")) {
			boolean isManagedDelivery = delivery.getDeliveryHistories().stream()
				.anyMatch(route -> route.getDeliverId().equals(userId));

			if (!isManagedDelivery) {
				throw new UnauthorizedException(ErrorCode.NOT_ALLOWED_HUB_MANAGER);
			}

		} else {
			if (!delivery.getDeliverId().equals(userId)) {
				throw new UnauthorizedException(ErrorCode.NOT_ALLOWED_COMPANY_DELIVER);
			}
		}
	}

	private static void validateHubManagerAccess(UserData userData, Delivery delivery) {
		UUID hubId = userData.hubId();
		boolean isManagedHub = delivery.getDeliveryHistories().stream()
			.anyMatch(route -> route.getDepartureHubId().equals(hubId));

		if (!isManagedHub) {
			throw new UnauthorizedException(ErrorCode.NOT_ALLOWED_HUB_MANAGER);
		}
	}

	private Delivery findDelivery(UUID deliveryId) {
		return deliveryRepository.findByIdAndIsDeletedFalse(deliveryId)
			.orElseThrow(DeliveryNotFoundException::new);
	}

	private UserData getUserData(UserDetailImpl userDetail) {
		// TODO user 서비스 연동 필요
		// UserData userData = userManager.getUserInfo(Long.parseLong(userDetail.userId()));
		// if (userData.id() == null) {
		// 	throw new FeignException();
		// }
		// return userData;
		return new UserData(1L, "test user", "test@mail.com", "testslackId", UserRole.COMPANY_MANAGER, "COMPANY", UUID.randomUUID(), UUID.fromString("c5013a5f-9193-4d6a-8e9d-2ce06de491d7"));
	}


}
