package com.msa.hub.domain.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_hub_route")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HubRoute extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_hub_id")
    private Hub sourceHub;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_hub_id")
    private Hub destinationHub;

    @Column(name="distance", nullable=false)
    private Double totalDistance;

    @Column(name="duration", nullable=false)
    private Long totalDuration;

    @OneToMany(mappedBy = "linkedRoute", cascade = CascadeType.ALL)
    private List<Waypoint> waypoints;

    @Builder(access = AccessLevel.PRIVATE)
    private HubRoute(Hub sourceHub, Hub destinationHub, Double totalDistance, Long totalDuration) {
        this.sourceHub = sourceHub;
        this.destinationHub = destinationHub;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
    }

    public static HubRoute createBy(Hub sourceHub, Hub destinationHub, Double totalDistance, Long totalDuration) {
        return HubRoute.builder()
                .sourceHub(sourceHub)
                .destinationHub(destinationHub)
                .totalDistance(totalDistance)
                .totalDuration(totalDuration)
                .build();
    }

    public void updateTotalDetail(Double totalDistance, Long totalDuration) {
        if (totalDistance != null) this.totalDistance = totalDistance;
        if (totalDuration != null) this.totalDuration = totalDuration;
    }

    public void updateWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public void softDeleteHubRoute(Long userId) {
        this.deleteBase(userId);
        for(Waypoint waypoint : this.waypoints) {
            waypoint.deleteBase(userId);
        }
    }
}
