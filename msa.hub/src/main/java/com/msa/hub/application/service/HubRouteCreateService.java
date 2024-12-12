package com.msa.hub.application.service;


import com.msa.hub.domain.model.Hub;
import com.msa.hub.domain.model.HubRoute;
import com.msa.hub.domain.model.Waypoint;
import com.msa.hub.domain.repository.HubRepository;
import com.msa.hub.domain.repository.HubRouteRepository;
import com.msa.hub.domain.service.DistanceCalculator;
import com.msa.hub.domain.service.WaypointSelector;
import com.msa.hub.exception.ErrorCode;
import com.msa.hub.exception.HubException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubRouteCreateService {

    private final HubRepository hubRepository;
    private final HubRouteRepository routeRepository;
    private final DistanceCalculator distanceCalculator;
    private final WaypointSelector waypointSelector;

    @Transactional
    public void createRoute(final String sourceId, final String destinationId) {
        Hub source = getHubOrException(sourceId);
        Hub destination = getHubOrException(destinationId);

        HubRoute route = initializeRoute(source, destination);

        List<Hub> allHubs = hubRepository.findAll();
        List<Waypoint> waypoints = createWaypoints(route, source, destination, allHubs);
        route.updateWaypoints(waypoints);

        routeRepository.save(route);
    }

    private Hub getHubOrException(final String id) {
        return hubRepository.findById(id)
                .orElseThrow(() -> new HubException(ErrorCode.HUB_NOT_FOUND));
    }

    private HubRoute initializeRoute(Hub source, Hub destination) {
        double totalDistance = calculateDistance(source, destination);
        long totalDuration = calculateDuration(totalDistance);

        return HubRoute.createBy(
                source,
                destination,
                totalDistance,
                totalDuration
        );
    }

    /*
    중간 경유지 생성
     */
    private List<Waypoint> createWaypoints(HubRoute route, Hub source, Hub destination, List<Hub> allHubs) {
        List<Hub> waypointsHubs = waypointSelector.findWaypoints(source, destination, allHubs);

        List<Waypoint> waypoints = new ArrayList<>();
        Hub current = source;

        for (Hub waypointHub : waypointsHubs) {
            Waypoint waypoint = createWaypoint(route, current, waypointHub);
            waypoints.add(waypoint);
            current = waypointHub;
        }

        // 마지막 허브까지의 경유지 추가
        if (!waypointsHubs.isEmpty()) {
            waypoints.add(createWaypoint(route, current, destination));
        }

        return waypoints;
    }

    private Waypoint createWaypoint(HubRoute route, Hub fromHub, Hub toHub) {
        double distance = calculateDistance(fromHub, toHub);
        int duration = calculateDuration(distance);
        return Waypoint.createBy(
                route,
                toHub,
                distance,
                duration
        );
    }

    private double calculateDistance(Hub from, Hub to) {
        return distanceCalculator.calculateDistance(
                from.getLatitude(), from.getLongitude(),
                to.getLatitude(), to.getLongitude()
        );
    }

    private int calculateDuration(double distance) {
        return distanceCalculator.calculateDuration(distance);
    }
}
