package com.msa.user.shipper.application;

import static com.msa.user.common.exception.ErrorCode.*;

import com.msa.user.shipper.application.dto.DeleteShipperResponse;
import com.msa.user.shipper.application.dto.ShipperAssignResponseDto;
import com.msa.user.shipper.application.dto.ShipperAssignResponseDto.AssignedPathDto;
import com.msa.user.shipper.application.dto.ShipperResponse;
import com.msa.user.shipper.domain.model.type.ShipperStatus;
import com.msa.user.shipper.domain.model.type.ShipperType;
import com.msa.user.shipper.exception.ShipperException;
import com.msa.user.shipper.domain.model.Shipper;
import com.msa.user.shipper.domain.repository.ShipperRepository;
import com.msa.user.domain.repository.UserRepository;
import com.msa.user.infrastructure.HubClient;
import com.msa.user.shipper.presentation.request.CreateShipperRequest;
import com.msa.user.shipper.presentation.request.ShipperAssignRequestDto;
import com.msa.user.shipper.presentation.request.UpdateShipperRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final HubClient hubClient;
    private final ShipperRepository shipperRepository;
    private final UserRepository userRepository;
    private final ConcurrentHashMap<UUID, AtomicInteger> hubShipperIndices = new ConcurrentHashMap<>();

    @Transactional
    public ShipperResponse createShipper(CreateShipperRequest request) {

        hubClient.verifyHub(request.hubId());

        // 배송 순번 결정 : 현재 최대 순번 + 1
        Integer maxOrder = shipperRepository.findMaxDeliveryOrder();
        Integer newOrder = (maxOrder == null) ? 1 : maxOrder + 1;

        Shipper shipper = Shipper.builder()
                .hubId(request.hubId())
                .type(request.type())
                .deliveryOrder(newOrder)
                .build();

        shipperRepository.save(shipper);

        return ShipperResponse.fromEntity(shipper);
    }

    public ShipperResponse getShipper(UUID shipperId) {
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ShipperException(SHIPPER_NOT_FOUND));

        return ShipperResponse.fromEntity(shipper);
    }

    public List<ShipperResponse> getShippers() {
        List<Shipper> shippers = shipperRepository.findAll();

        return shippers.stream()
                .map(ShipperResponse::fromEntity)
                .toList();
    }

    @Transactional
    public ShipperResponse updateShipper(UUID shipperId, UpdateShipperRequest request) {
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ShipperException(SHIPPER_NOT_FOUND));

        hubClient.verifyHub(request.hubId());

        shipper.updateShipper(request.hubId(), request.type());

        return ShipperResponse.fromEntity(shipper);
    }

    @Transactional
    public DeleteShipperResponse deleteShipper(UUID shipperId) {
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ShipperException(SHIPPER_NOT_FOUND));

        shipper.delete();

        return DeleteShipperResponse.fromEntity(shipper);
    }

    /**
     * 배송 담당자를 할당
     * 주어진 배송 경로 리스트에 대해 출발 허브 배송 담당자와 도착 허브의
     * 회사 배송 담당자를 순차적으로 할당한다. 경로당 두 명의 배송 담당자가 매칭
     * @param request 배송 담당자 할당 요청 데이터
     * @return 할당된 배송 경로와 담당자 정보를 반환
     * @throws ShipperException 허브 검증 실패 또는 배송 담당자를 찾을 수 없는 경우 예외
     */
    @Transactional
    public ShipperAssignResponseDto assignShippers(ShipperAssignRequestDto request) {

        List<AssignedPathDto> assignedPath = new ArrayList<>();

        for (ShipperAssignRequestDto.PathDto path : request.paths()) {
            Boolean isHubValid = hubClient.verifyHub(path.departureHubId());
            if (!isHubValid) {
                throw new ShipperException(INVALID_HUB_ID);
            }

            // 출발 허브 배송자 담당자 할당
            Shipper hubShipper = assignShipperToHub(path.departureHubId());

            // Company 배송자 담당자 할당
            Shipper companyShipper =
                    assignShipperToCompany(path.destinationHubId(), request.companyDeliverId());

            AssignedPathDto assignedPathDto = new AssignedPathDto(
                    path.nodeId(),
                    hubShipper.getId().toString(),
                    companyShipper.getId().toString()
            );

            assignedPath.add(assignedPathDto);
        }
        return new ShipperAssignResponseDto(assignedPath, request.companyDeliverId());
    }

    /**
     * 허브에 배송 담당자를 할당(Round-Robin 방식 사용).
     * 출발 허브의 배송 담당자 리스트 중에서 하나를 순차적으로 선택.
     * 선택된 배송 담당자의 상태는 'DELIVERING'으로 변경
     * @param departureHubId 출발 허브의 ID
     * @return 할당된 배송 담당자를 반환
     * @throws ShipperException 허브 ID가 유효하지 않거나 사용 가능한 배송 담당자가 없는 경우 예외
     */
    private Shipper assignShipperToHub(String departureHubId) {
        UUID hubId;
        try {
            hubId = UUID.fromString(departureHubId);
        } catch (IllegalArgumentException e) {
            throw new ShipperException(INVALID_HUB_ID);
        }

        List<Shipper> availableHubShippers = shipperRepository
                .findAvailableShippers(hubId, ShipperType.HUB);
        if (availableHubShippers.isEmpty()) {
            throw new ShipperException(NO_AVAILABLE_HUB_SHIPPERS);
        }

        // Round-Robin 방식으로 할당
        AtomicInteger index =
                hubShipperIndices.computeIfAbsent(hubId, k -> new AtomicInteger(0));
        int currentIndex = index.getAndIncrement();
        Shipper selectedShipper = availableHubShippers.get(
                currentIndex % availableHubShippers.size());

        // 상태 업데이트 (DELIVERING 상태로 변경)
        selectedShipper.updateStatus(ShipperStatus.DELIVERING);
        shipperRepository.save(selectedShipper);

        return selectedShipper;
    }

    /**
     * 도착 허브의 회사 배송 담당자를 할당.
     * 요청된 회사 배송 담당자 ID에 따라 해당 배송 담당자의 유효성을 확인하고,
     * 허브 ID와 상태를 검증한 뒤 'DELIVERING' 상태로 변경
     * @param destinationHubId 도착 허브의 ID
     * @param companyDeliverId 회사 배송 담당자의 ID
     * @return 할당된 회사 배송 담당자를 반환한다.
     * @throws ShipperException 배송 담당자 ID 불일치 또는 상태가 유효하지 않은 경우 예외를 던진다.
     */
    private Shipper assignShipperToCompany(String destinationHubId, String companyDeliverId) {
        UUID companyShipperId;
        try {
            companyShipperId = UUID.fromString(companyDeliverId);
        } catch (IllegalArgumentException e) {
            throw new ShipperException(INVALID_COMPANY_SHIPPER_ID);
        }

        Shipper companyShipper = shipperRepository.findById(companyShipperId)
                .orElseThrow(() -> new ShipperException(COMPANY_SHIPPER_NOT_FOUND));

        // 회사 배송 담당자의 허브 ID가 도착 허브 ID와 일치하는지 확인
        if (!companyShipper.getHubId().equals(destinationHubId)) {
            throw new ShipperException(COMPANY_SHIPPER_HUB_MISMATCH);
        }

        if (companyShipper.getStatus() != ShipperStatus.AVAILABLE) {
            throw new ShipperException(COMPANY_SHIPPER_NOT_AVAILABLE);
        }

        companyShipper.updateStatus(ShipperStatus.DELIVERING);
        shipperRepository.save(companyShipper);

        return companyShipper;
    }


}
