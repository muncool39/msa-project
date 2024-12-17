package com.msa.delivery.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.delivery.application.client.DeliveryRouteManager;
import com.msa.delivery.application.client.UserManager;
import com.msa.delivery.application.client.dto.request.GetDeliveryWorkersRequest;
import com.msa.delivery.application.client.dto.response.DeliveryRoutesData;
import com.msa.delivery.application.client.dto.response.DeliveryWorkersData;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.DeliveryRouteHistory;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.exception.BusinessException.FeignException;
import com.msa.delivery.exception.ErrorCode;
import com.msa.delivery.presentation.request.CreateDeliveryRequest;
import com.msa.delivery.presentation.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CreateDeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final DeliveryRouteManager deliveryRouteManager;
	private final UserManager userManager;

	public CreateDeliveryService(DeliveryRepository deliveryRepository,
		@Qualifier("deliveryRouteClient") DeliveryRouteManager deliveryRouteManager,
		@Qualifier("userClient") UserManager userManager) {
		this.deliveryRepository = deliveryRepository;
		this.deliveryRouteManager = deliveryRouteManager;
		this.userManager = userManager;
	}

	@Transactional
	public Delivery createDelivery(CreateDeliveryRequest request) {
		UUID departureHubId = request.departureHubId();
		UUID destinationHubId = request.destinationHubId();

		// 1. 배송요청 생성
		Delivery delivery = Delivery.create(request.orderId(), request.receiverCompanyId(), departureHubId, destinationHubId,
			request.address(), request.receiverName(), request.receiverSlackId());

		// 2. 배송경로 생성 요청
		log.info("[배송 경로 요청] 출발허브 : {} , 도착허브 : {} ", departureHubId, destinationHubId);
		List<DeliveryRouteHistory> histories = getDeliveryRoutes(departureHubId, destinationHubId);
		delivery.addDeliveryHistories(histories);
		deliveryRepository.save(delivery);

		// 3. 배송 담당자 할당 받기
		GetDeliveryWorkersRequest deliveryWorkerRequest = createDeliveryWorkerRequest(histories);
		ApiResponse<DeliveryWorkersData> response = userManager.assignDeliveryWorkers(deliveryWorkerRequest);
		DeliveryWorkersData workersData = response.data();
		if (workersData == null) {
			throw new FeignException(ErrorCode.DELIVER_ASSIGN_SERVICE_ERROR);
		}

		mappingDeliveryWorker(delivery, workersData, histories);

		return delivery;
	}

	private static void mappingDeliveryWorker(Delivery delivery, DeliveryWorkersData workersData,
		List<DeliveryRouteHistory> histories) {

		delivery.assignCompanyDeliver(workersData.companyDeliverId());

		Map<UUID, DeliveryRouteHistory> routeMapByDepartureHubId = histories.stream()
			.collect(Collectors.toMap(DeliveryRouteHistory::getDepartureHubId, history -> history));

		// 각 경로별로 배송 담당자 할당
		workersData.paths().forEach(route -> {
			DeliveryRouteHistory routeHistory = routeMapByDepartureHubId.get(UUID.fromString(route.nodeId()));
			if (routeHistory != null) {
				routeHistory.assignHubDeliver(route.hubDeliverId());
			}
		});
	}

	private List<DeliveryRouteHistory> getDeliveryRoutes(UUID departureHubId, UUID destinationHubId) {
		List<DeliveryRouteHistory> histories = new ArrayList<>();
		DeliveryRoutesData routesData = deliveryRouteManager.getDeliveryRoutes(departureHubId, destinationHubId);
		if (routesData.hubRouteId() == null) {
			throw new FeignException(ErrorCode.HUB_SERVICE_ERROR);
		}

		if (!routesData.waypoints().isEmpty()) {
			histories = createWaypointDeliveryHistories(routesData, histories);
		} else {
			// 경유지가 없는 경우
			histories.add(DeliveryRouteHistory.create(1, null, departureHubId, destinationHubId,
				routesData.totalDistance(), routesData.totalDuration()));
		}

		return histories;
	}

	private List<DeliveryRouteHistory> createWaypointDeliveryHistories(DeliveryRoutesData routesData, List<DeliveryRouteHistory> histories) {
		List<DeliveryRoutesData.WaypointResponse> waypoints = routesData.waypoints();

		// 출발지 -> 첫 경유지
		var firstWaypoint = routesData.waypoints().get(0);
		histories.add(DeliveryRouteHistory.create(
			1,
			null,
			UUID.fromString(routesData.sourceHubId()),
			UUID.fromString(firstWaypoint.hubId()),
			firstWaypoint.distanceFromPrevious(),
			firstWaypoint.durationFromPrevious()
		));

		// 경유지 간 이동
		for (int i = 0; i < waypoints.size() - 1; i++) {
			var current = waypoints.get(i);
			var next = waypoints.get(i + 1);

			histories.add(DeliveryRouteHistory.create(
				i + 2,  // (출발->첫경유가 1)
				null,
				UUID.fromString(current.hubId()),
				UUID.fromString(next.hubId()),
				next.distanceFromPrevious(),
				next.durationFromPrevious()
			));
		}

		return histories;
	}

	private GetDeliveryWorkersRequest createDeliveryWorkerRequest(List<DeliveryRouteHistory> histories) {

		List<GetDeliveryWorkersRequest.DeliveryNode> nodes = histories.stream()
			.map(history -> new GetDeliveryWorkersRequest.DeliveryNode(
				String.valueOf(history.getId()),
				history.getSequence(),
				String.valueOf(history.getDepartureHubId()),
				String.valueOf(history.getDestinationHubId())
			))
			.collect(Collectors.toList());

		return new GetDeliveryWorkersRequest(nodes);
	}

}
