package com.msa.hub.application.service;


import com.msa.hub.application.dto.HubRouteDetailResponse;
import com.msa.hub.application.dto.HubRouteResponse;
import com.msa.hub.domain.repository.HubRouteRepository;
import com.msa.hub.common.exception.ErrorCode;
import com.msa.hub.common.exception.HubException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubRouteReadService {

    private final HubRouteRepository hubRouteRepository;

    public HubRouteDetailResponse getHubRouteDetail(String id) {
        return HubRouteDetailResponse.convertToResponse(
                hubRouteRepository.findById(id)
                        .orElseThrow(()-> new HubException(ErrorCode.HUB_ROUTE_NOT_FOUND))
        );
    }

    public Page<HubRouteResponse> findHubRoutes(
            Pageable pageable, String sourceHubId, String destinationHubId
    ) {
        return hubRouteRepository
                .findHubRoutesWith(pageable, sourceHubId, destinationHubId)
                .map(HubRouteResponse::convertToResponse);
    }

    public HubRouteDetailResponse findHubRouteBy(String sourceHubId, String destinationHubId) {
        return HubRouteDetailResponse.convertToResponse(
                hubRouteRepository.findBySourceHubIdAndDestinationHubId(sourceHubId, destinationHubId)
                        .orElseThrow(()-> new HubException(ErrorCode.HUB_ROUTE_NOT_FOUND))
        );
    }

}
