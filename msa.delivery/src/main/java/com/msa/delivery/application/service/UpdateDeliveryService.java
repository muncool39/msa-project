package com.msa.delivery.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.UserData;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.DeliveryRouteHistory;
import com.msa.delivery.domain.entity.enums.UserRole;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.exception.BusinessException.DeliveryNotFoundException;
import com.msa.delivery.exception.BusinessException.FeignException;
import com.msa.delivery.exception.BusinessException.UnauthorizedException;
import com.msa.delivery.exception.ErrorCode;
import com.msa.delivery.presentation.request.UpdateDeliveryRequest;

@Service
public class UpdateDeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final UserManager userManager;

	public UpdateDeliveryService(DeliveryRepository deliveryRepository,
		@Qualifier("userClient") UserManager userManager) {
		this.deliveryRepository = deliveryRepository;
		this.userManager = userManager;
	}

	@Transactional
	public void updateDelivery(UUID deliveryId, UpdateDeliveryRequest request, String userId, UserRole role) {
		Delivery delivery = findDelivery(deliveryId);
		UserData userData = getUserData(userId);
		validatePermissionByRole(role, delivery, userData, request);
		completeDelivery(request, userId, userData, delivery);
	}

	private void validatePermissionByRole(UserRole role, Delivery delivery, UserData userData, UpdateDeliveryRequest request) {
		switch (role) {
			case MASTER -> { }
			case HUB_MANAGER -> { // 담당 허브의 배송만 업데이트 가능
				UUID hubId = userData.hubId();
				boolean isManagedHub = delivery.getDeliveryHistories().stream()
					.anyMatch(route -> route.getDepartureHubId().equals(hubId));

				if (!isManagedHub) throw new UnauthorizedException(ErrorCode.NOT_ALLOWED_HUB_MANAGER);
			}
			case DELIVERY_MANAGER -> {validateDeliveryManagerPermission(delivery, userData, request);}
		}

	}

	private void validateDeliveryManagerPermission(Delivery delivery, UserData userData, UpdateDeliveryRequest request) {
		if ("HUB".equals(userData.type())) {
			DeliveryRouteHistory currentRoute = delivery.findCurrentRoute(request.routeId());
			currentRoute.validateHubDeliverAccess(userData.id());
		} else {
			delivery.validateCompanyDeliverAccess(userData.id());
		}
	}

	private void completeDelivery(UpdateDeliveryRequest request, String userId, UserData userData, Delivery delivery) {
		if (userData.type() == null) { // 마스터, 허브 매니저
			if (request.routeId() != null) {
				updateHubDeliveryRoute(delivery, userId, request);
			} else {
				updateCompanyDelivery(delivery, userId);
			}
			return;
		}

		switch (userData.type()) {
			case "HUB" -> updateHubDeliveryRoute(delivery, userId, request);
			case "COMPANY" ->  updateCompanyDelivery(delivery, userId);
			default -> throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
		}
	}

	private void updateHubDeliveryRoute(Delivery delivery, String userId, UpdateDeliveryRequest request) {
		DeliveryRouteHistory currentRoute = delivery.findCurrentRoute(request.routeId());
		delivery.completeHubDelivery(currentRoute, userId, request.actualDistance(), request.actualTime());
	}

	private void updateCompanyDelivery(Delivery delivery, String userId) {
		delivery.completeDelivery(Long.parseLong(userId));
	}

	private Delivery findDelivery(UUID deliveryId) {
		return deliveryRepository.findByIdAndIsDeletedFalse(deliveryId)
			.orElseThrow(DeliveryNotFoundException::new);
	}

	private UserData getUserData(String userId) {
		// TODO 유저 연동 필요
		// UserData userData = userManager.getUserInfo(Long.parseLong(userId));
		// if (userData.id() == null) {
		// 	throw new FeignException();
		// }
		//
		// return userData;
	//	return new UserData(2L, "마스터", "master@master.com", "test", UserRole.MASTER, null, null, null);
		return new UserData(1L, "업체배송담당자", "master@master.com", "test", UserRole.DELIVERY_MANAGER, "COMPANY", null, UUID.randomUUID());
	}
}
