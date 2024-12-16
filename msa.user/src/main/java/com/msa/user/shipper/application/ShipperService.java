package com.msa.user.shipper.application;

import static com.msa.user.common.exception.ErrorCode.*;

import com.msa.user.shipper.application.dto.DeleteShipperResponse;
import com.msa.user.shipper.application.dto.ShipperResponse;
import com.msa.user.shipper.exception.ShipperException;
import com.msa.user.shipper.domain.model.Shipper;
import com.msa.user.shipper.domain.repository.ShipperRepository;
import com.msa.user.domain.repository.UserRepository;
import com.msa.user.infrastructure.HubClient;
import com.msa.user.shipper.presentation.request.CreateShipperRequest;
import com.msa.user.shipper.presentation.request.UpdateShipperRequest;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final HubClient hubClient;
    private final ShipperRepository shipperRepository;
    private final UserRepository userRepository;

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




}
