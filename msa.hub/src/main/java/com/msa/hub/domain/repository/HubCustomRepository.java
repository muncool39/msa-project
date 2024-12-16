package com.msa.hub.domain.repository;

import com.msa.hub.domain.model.Hub;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubCustomRepository {
    Page<Hub> findHubsWith(
            Pageable pageable, String name, String city, String district, String streetName);
}
