package com.msa.delivery.application.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.msa.delivery.application.dto.DeliveryRoutesData;
import com.msa.delivery.application.dto.DeliveryWorkersData;
import com.msa.delivery.domain.entity.Address;
import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.presentation.request.CreateDeliveryRequest;

@ExtendWith(MockitoExtension.class)
class CreateDeliveryServiceTest {

	@Mock
	private DeliveryRepository deliveryRepository;

	@Mock
	private DeliveryRouteManager deliveryRouteManager;

	@Mock
	private DeliveryAssigner deliveryAssigner;

	private CreateDeliveryService createDeliveryService;

	@BeforeEach
	void setUp() {
		createDeliveryService = new CreateDeliveryService(
			deliveryRepository,
			deliveryRouteManager,
			deliveryAssigner
		);

	}

	@Test
	@DisplayName("배송이 성공적으로 생성되어야 한다")
	void createDeliverySuccess() {
		// Given
		UUID orderId = UUID.randomUUID();
		UUID supplierCompanyId = UUID.randomUUID();
		UUID departureHubId = UUID.randomUUID();
		UUID destinationHubId = UUID.randomUUID();
		Address address = Address.of("서울시", "강남구", "테헤란로", "123", "4층 401호");
		String receiverName = "홍길동";
		String receiverSlackId = "test1234";

		UUID waypoint1Id = UUID.randomUUID();
		UUID waypoint2Id = UUID.randomUUID();

		CreateDeliveryRequest request = new CreateDeliveryRequest(orderId, supplierCompanyId, departureHubId,
			destinationHubId, address, receiverName, receiverSlackId);

		DeliveryRoutesData routesData = new DeliveryRoutesData(
			departureHubId.toString(), destinationHubId.toString(), 10.0, 30L,
			List.of(
				new DeliveryRoutesData.WaypointResponse(waypoint1Id.toString(), 12.5, 10, 1),
				new DeliveryRoutesData.WaypointResponse(waypoint2Id.toString(), 10.0, 20, 2)
			)
		);
		when(deliveryRouteManager.getDeliveryRoutes(departureHubId, destinationHubId))
			.thenReturn(routesData);

		DeliveryWorkersData workersData = new DeliveryWorkersData(
			List.of(new DeliveryWorkersData.DeliveryPathNode(UUID.randomUUID(), 1L)), 1L);
		when(deliveryAssigner.assignDeliveryWorkers(any()))
			.thenReturn(workersData);

		when(deliveryRepository.save(any(Delivery.class)))
			.thenAnswer(o -> o.getArgument(0));

		// When
		Delivery result = createDeliveryService.createDelivery(request);


		// Then
		assertThat(result).isNotNull();
		verify(deliveryRepository).save(any(Delivery.class));
		assertThat(result.getOrderId()).isEqualTo(orderId);
		assertThat(result.getDepartureHubId()).isEqualTo(departureHubId);
		assertThat(result.getDestinationHubId()).isEqualTo(destinationHubId);
		assertThat(result.getAddress()).isEqualTo(address);


		// // DeliveryHistories 검증
		assertThat(result.getDeliveryHistories()).hasSize(3);  // 총 3개의 history가 생성되어야 함
		//
		// // 각 구간별 검증
		var histories = result.getDeliveryHistories();

		// 출발지 -> 첫 경유지
		assertThat(histories.get(0).getDepartureHubId()).isEqualTo(departureHubId);
		assertThat(histories.get(0).getDestinationHubId()).isEqualTo(waypoint1Id);
		assertThat(histories.get(0).getSequence()).isEqualTo(1);

		// 첫 경유지 -> 두번째 경유지
		assertThat(histories.get(1).getDepartureHubId()).isEqualTo(waypoint1Id);
		assertThat(histories.get(1).getDestinationHubId()).isEqualTo(waypoint2Id);
		assertThat(histories.get(1).getSequence()).isEqualTo(2);

		// 마지막 경유지 -> 도착지
		assertThat(histories.get(2).getDepartureHubId()).isEqualTo(waypoint2Id);
		assertThat(histories.get(2).getDestinationHubId()).isEqualTo(destinationHubId);
		assertThat(histories.get(2).getSequence()).isEqualTo(3);
	}
}