package com.msa.hub.domain.service;


import com.msa.hub.domain.model.Hub;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class WaypointSelector {

    public List<Hub> findWaypoints(Hub source, Hub destination, List<Hub> hubs) {
        List<Hub> remainingHubs = new ArrayList<>(hubs);
        remainingHubs.remove(source);
        remainingHubs.remove(destination);

        List<Hub> waypoints = new ArrayList<>();
        Hub current = source;

        while (true) {
            double distanceToDestination = calculateDistance(
                    current.getLatitude(), current.getLongitude(),
                    destination.getLatitude(), destination.getLongitude()
            );
            if (distanceToDestination <= 200) {
                break;
            }
            Hub finalCurrent = current;
            Hub nextWaypoint = remainingHubs.stream()
                    .min(Comparator.comparingDouble(hub ->
                            calculateDistance(
                                    finalCurrent.getLatitude(), finalCurrent.getLongitude(),
                                    hub.getLatitude(), hub.getLongitude())))
                    .orElse(null);
            if (nextWaypoint == null) {
                break;
            }

            waypoints.add(nextWaypoint);
            remainingHubs.remove(nextWaypoint);
            current = nextWaypoint;
        }
        return waypoints;
    }


    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return new DistanceCalculator().calculateDistance(lat1, lon1, lat2, lon2);
    }
}
