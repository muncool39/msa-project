package com.msa.hub.domain.repository;


import com.msa.hub.domain.model.HubRoute;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRouteRepository extends JpaRepository<HubRoute, String>, HubRouteCustomRepository{
    Optional<HubRoute> findBySourceHubIdAndDestinationHubId(String sourceHubId, String destinationHubId);
}
