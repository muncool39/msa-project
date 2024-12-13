package com.msa.hub.domain.service;


import org.springframework.stereotype.Component;

/**
 * 위도 경도 정보로 거리 계산
 */
@Component
public class DistanceCalculator {

    private static final double EARTH_RADIUS = 6371;

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public int calculateDuration(double distance) {
        final int AVERAGE_SPEED_KMH = 60;
        return (int) (distance / AVERAGE_SPEED_KMH * 60);
    }

}
