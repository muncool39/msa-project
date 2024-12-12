package com.msa.hub.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_hub_route_way_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Waypoint {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    private HubRoute linkedRoute;

    @ManyToOne(fetch = FetchType.LAZY)
    private Hub hub;

    @Column(name="distance_from_previous", nullable=false)
    private double distanceFromPrevious;

    @Column(name="duration_from_previuos", nullable=false)
    private int durationFromPrevious;

    @Builder(access = AccessLevel.PRIVATE)
    private Waypoint(HubRoute linkedRoute, Hub hub, double distanceFromPrevious, int durationFromPrevious) {
        this.linkedRoute = linkedRoute;
        this.hub = hub;
        this.distanceFromPrevious = distanceFromPrevious;
        this.durationFromPrevious = durationFromPrevious;
    }

    public static Waypoint createBy(HubRoute route, Hub hub, double distanceFromPrevious, int durationFromPrevious) {
        return Waypoint.builder()
                .linkedRoute(route)
                .hub(hub)
                .distanceFromPrevious(distanceFromPrevious)
                .durationFromPrevious(durationFromPrevious)
                .build();
    }

}
