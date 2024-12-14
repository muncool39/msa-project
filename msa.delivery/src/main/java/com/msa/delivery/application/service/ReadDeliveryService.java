package com.msa.delivery.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.UserData;
import com.msa.delivery.config.UserDetailImpl;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.enums.UserRole;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.exception.BusinessException.DeliveryNotFoundException;
import com.msa.delivery.exception.BusinessException.UnauthorizedException;

@Service
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
			case HUB_MANAGER, COMPANY_MANAGER -> {
				validateHubManagerAccess(userData, delivery);
				return delivery;
			}
			case DELIVERY_MANAGER -> {
				validateDeliveryManagerAccess(userData, delivery, userId);
				return delivery;
			}
		}

		return delivery;
	}

	private static void validateDeliveryManagerAccess(UserData userData, Delivery delivery, Long userId) {
		if (userData.type().equals("HUB")) {
			boolean isManagedDelivery = delivery.getDeliveryHistories().stream()
				.anyMatch(route -> route.getDeliverId().equals(userId));

			if (!isManagedDelivery) {
				throw new UnauthorizedException();
			}

		} else {
			if (!delivery.getDeliverId().equals(userId)) {
				throw new UnauthorizedException();
			}
		}
	}

	private static void validateHubManagerAccess(UserData userData, Delivery delivery) {
		UUID hubId = userData.hubId();
		boolean isManagedHub = delivery.getDeliveryHistories().stream()
			.anyMatch(route -> route.getDepartureHubId().equals(hubId));

		if (!isManagedHub) {
			throw new UnauthorizedException();
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
		return new UserData(1L, "test user", "test@mail.com", "testslackId", UserRole.COMPANY_MANAGER, null, UUID.randomUUID(),
			UUID.randomUUID());
	}
}
