package com.msa.hub.domain.repository;

import com.msa.hub.domain.model.HubRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubRouteCustomRepository {
    Page<HubRoute> findHubRoutesWith(Pageable pageable, String sourceHubId, String destinationHubId);
}
