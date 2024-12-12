package com.msa.delivery.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.delivery.application.dto.DeliveryRoutesData;
import com.msa.delivery.application.dto.DeliveryWorkersData;
import com.msa.delivery.application.dto.GetDeliveryWorkersRequest;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.DeliveryRouteHistory;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.presentation.request.CreateDeliveryRequest;

@Service
public class CreateDeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final DeliveryRouteManager deliveryRouteManager;
	private final DeliveryAssigner deliveryAssigner;

	public CreateDeliveryService(DeliveryRepository deliveryRepository,
		@Qualifier("deliveryRouteClient") DeliveryRouteManager deliveryRouteManager,
		@Qualifier("deliveryAssignClient") DeliveryAssigner deliveryAssigner) {
		this.deliveryRepository = deliveryRepository;
		this.deliveryRouteManager = deliveryRouteManager;
		this.deliveryAssigner = deliveryAssigner;
	}

	@Transactional
	public Delivery createDelivery(CreateDeliveryRequest request) {
		UUID departureHubId = request.departureHubId();
		UUID destinationHubId = request.destinationHubId();

		// 1. 배송요청 생성
		Delivery delivery = Delivery.create(request.orderId(), departureHubId, destinationHubId,
			request.address(), request.receiverName(), request.receiverSlackId());

		// 2. 배송경로 생성 요청
		List<DeliveryRouteHistory> histories = getDeliveryRoutes(departureHubId, destinationHubId);
		delivery.addDeliveryHistories(histories);
		deliveryRepository.save(delivery);

		// 3. 배송 담당자 할당 받기
		GetDeliveryWorkersRequest deliveryWorkerRequest = createDeliveryWorkerRequest(histories);
		DeliveryWorkersData workersData = deliveryAssigner.assignDeliveryWorkers(deliveryWorkerRequest);
		mappingDeliveryWorker(delivery, workersData, histories);

		return delivery;
	}

	private static void mappingDeliveryWorker(Delivery delivery, DeliveryWorkersData workersData,
		List<DeliveryRouteHistory> histories) {

		delivery.assignCompanyDeliveryWorker(workersData.companyDeliveryId());

		// DeliveryRouteHistory를 nodeId로 조회할 수 있도록 Map으로 변환
		Map<UUID, DeliveryRouteHistory> historyByNodeId = histories.stream()
			.collect(Collectors.toMap(DeliveryRouteHistory::getId, history -> history));

		// 각 경로별로 배송 담당자 할당
		workersData.path().forEach(node -> {
			DeliveryRouteHistory history = historyByNodeId.get(node.nodeId());
			if (history != null) {
				history.assignDeliveryWorker(node.deliveryId());
			}
		});
	}

	private List<DeliveryRouteHistory> getDeliveryRoutes(UUID departureHubId, UUID destinationHubId) {
		List<DeliveryRouteHistory> histories = new ArrayList<>();
		DeliveryRoutesData routesData = deliveryRouteManager.getDeliveryRoutes(departureHubId, destinationHubId);

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

		// 마지막 경유지 -> 도착지
		var lastWaypoint = waypoints.get(waypoints.size() - 1);
		histories.add(DeliveryRouteHistory.create(
			waypoints.size() + 1,
			null,
			UUID.fromString(lastWaypoint.hubId()),
			UUID.fromString(routesData.destinationId()),
			0,
			0
		));

		return histories;
	}

	private GetDeliveryWorkersRequest createDeliveryWorkerRequest(List<DeliveryRouteHistory> histories) {

		List<GetDeliveryWorkersRequest.DeliveryNode> nodes = histories.stream()
			.map(history -> new GetDeliveryWorkersRequest.DeliveryNode(
				history.getId(),
				history.getSequence(),
				history.getDepartureHubId(),
				history.getDestinationHubId()
			))
			.collect(Collectors.toList());

		return new GetDeliveryWorkersRequest(nodes);
	}

}
