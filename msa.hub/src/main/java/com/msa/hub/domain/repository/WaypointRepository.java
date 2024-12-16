package com.msa.hub.domain.repository;

import com.msa.hub.domain.model.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaypointRepository extends JpaRepository<Waypoint, String> {
}
