package com.msa.user.shipper.application;

import static com.msa.user.common.exception.ErrorCode.*;

import com.msa.user.shipper.application.dto.DeleteShipperResponse;
import com.msa.user.shipper.application.dto.ShipperAssignDetailDto;
import com.msa.user.shipper.application.dto.ShipperAssignResponseDto;
import com.msa.user.shipper.application.dto.ShipperResponse;
import com.msa.user.shipper.domain.model.type.ShipperStatus;
import com.msa.user.shipper.domain.model.type.ShipperType;
import com.msa.user.shipper.exception.ShipperException;
import com.msa.user.shipper.domain.model.Shipper;
import com.msa.user.shipper.domain.repository.ShipperRepository;
import com.msa.user.domain.repository.UserRepository;
import com.msa.user.infrastructure.HubClient;
import com.msa.user.shipper.presentation.request.CreateShipperRequest;
import com.msa.user.shipper.presentation.request.ShipperAssignRequest;
import com.msa.user.shipper.presentation.request.UpdateShipperRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipperService {

    private final HubClient hubClient;
    private final ShipperRepository shipperRepository;
    private final UserRepository userRepository;

    @Transactional
    public ShipperResponse createShipper(CreateShipperRequest request, Long userId) {

        hubClient.verifyHub(request.hubId());

        // 배송 순번 결정 : 현재 최대 순번 + 1
        Integer maxOrder = shipperRepository.findMaxDeliveryOrder();
        Integer newOrder = (maxOrder == null) ? 1 : maxOrder + 1;

        Shipper shipper = Shipper.builder()
                .userId(userId)
                .hubId(request.hubId())
                .type(request.type())
                .deliveryOrder(newOrder)
                .build();

        shipperRepository.save(shipper);

        return ShipperResponse.fromEntity(shipper);
    }

    public ShipperResponse getShipper(Long shipperId) {
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
    public ShipperResponse updateShipper(Long shipperId, UpdateShipperRequest request) {
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ShipperException(SHIPPER_NOT_FOUND));

        hubClient.verifyHub(request.hubId());

        shipper.updateShipper(request.hubId(), request.type());

        return ShipperResponse.fromEntity(shipper);
    }

    @Transactional
    public DeleteShipperResponse deleteShipper(Long shipperId) {
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ShipperException(SHIPPER_NOT_FOUND));

        shipper.delete();

        return DeleteShipperResponse.fromEntity(shipper);
    }

    /**
     * 배송 담당자들을 경로에 따라 할당 허브 배송 담당자는 경유지 수 + 1명 할당되며,
     * 업체 배송 담당자는 마지막 경로에만 배정
     *
     * @param request 배송 담당자 할당 요청 데이터 (경로 목록 및 관련 정보 포함)
     * @return ShipperAssignResponseDto 할당된 배송 경로 및 배송 담당자 정보 반환
     * @throws ShipperException 허브 검증 실패, 요청 데이터가 비어있거나 배송 담당자가 부족한 경우
     */
    @Transactional
    public ShipperAssignResponseDto assignShippers(ShipperAssignRequest request) {
        for (ShipperAssignRequest.PathDto path : request.paths()) {

            hubClient.verifyHub(path.departureHubId());
            hubClient.verifyHub(path.destinationHubId());
        }

        // 전체 경로에 따른 배송 담당자 배정
        List<ShipperAssignDetailDto> assignedShippers = new ArrayList<>();

        if (request.paths().isEmpty()) {
            throw new ShipperException(INVALID_REQUEST);
        }

        //허브 배송 담당자배정
        int pathCount = request.paths().size() + 1;

        List<Shipper> hubDeliverShippers =
                shipperRepository.findTopNByTypeAndStatusOrderByCreatedAtAsc(
                        ShipperType.HUB, ShipperStatus.AVAILABLE, pathCount);
        log.info("hubDeliverShippers: {}", hubDeliverShippers);

        if (hubDeliverShippers.size() < pathCount) {
            throw new ShipperException(INSUFFICIENT_DELIVERY_MANAGERS);
        }

        // 업체 배송 담당자 1명 배정
        List<Shipper> companyDeliverShippers =
                shipperRepository.findTop1ByTypeAndStatusOrderByCreatedAtAsc(
                        ShipperType.COMPANY, ShipperStatus.AVAILABLE);
        if (companyDeliverShippers.isEmpty()) {
            throw new ShipperException(INSUFFICIENT_DELIVERY_MANAGERS);
        }

        Shipper companyDeliverShipper = companyDeliverShippers.get(0);

        // 허브 배송 담당자 배정 (경로 개수에 맞춰 순차적으로 배정)
        for (int i = 0; i < pathCount; i++) {
            ShipperAssignRequest.PathDto path =
                    request.paths().get(Math.min(i, request.paths().size() - 1));
            Shipper hubShipper = hubDeliverShippers.get(i);

            hubShipper.updateStatus(ShipperStatus.DELIVERING);
            assignedShippers.add(new ShipperAssignDetailDto(
                    path.nodeId(), hubShipper.getId(), null));
        }

        //업체 배송 담당자 1명 배정 (마지막 경로에)
        ShipperAssignRequest.PathDto lastPathDto = request.paths()
                .get(request.paths().size() - 1);
        companyDeliverShipper.updateStatus(ShipperStatus.DELIVERING); // 상태 업데이트
        assignedShippers.add(new ShipperAssignDetailDto(
                lastPathDto.nodeId(),
                null,
                companyDeliverShipper.getId()
        ));

        return new ShipperAssignResponseDto(assignedShippers, companyDeliverShipper.getId());
    }

}
