package com.msa.hub.application.service;

import com.msa.hub.common.exception.ErrorCode;
import com.msa.hub.common.exception.HubException;
import com.msa.hub.domain.model.HubRoute;
import com.msa.hub.domain.model.Waypoint;
import com.msa.hub.domain.repository.HubRouteRepository;
import com.msa.hub.domain.repository.WaypointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HubRouteUpdateService {
    private final HubRouteRepository hubRouteRepository;
    private final WaypointRepository waypointRepository;

    public void updateHubRouteDetail(String routeId, Double totalDistance, Long totalDuration) {
        HubRoute hubRoute = hubRouteRepository.findById(routeId)
                .orElseThrow(()-> new HubException(ErrorCode.HUB_ROUTE_NOT_FOUND));
        hubRoute.updateTotalDetail(totalDistance, totalDuration);
    }

    public void updateWaypointDetail(
            String waypointId, Double distanceFromPrevious, Integer durationFromPrevious
    ) {
        Waypoint waypoint = waypointRepository.findById(waypointId)
                .orElseThrow(()-> new HubException(ErrorCode.WAYPOINT_NOT_FOUND));
        waypoint.updateDetail(distanceFromPrevious, durationFromPrevious);
        /* TODO: 허브 루트 총 거리 수정
        HubRoute hubRoute = hubRouteRepository.findById(waypoint.getLinkedRoute().getId())
               .orElseThrow(()-> new HubException(ErrorCode.HUB_ROUTE_NOT_FOUND));
         */
    }
}
