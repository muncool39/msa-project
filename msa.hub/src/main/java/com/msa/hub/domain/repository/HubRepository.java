package com.msa.hub.domain.repository;

import com.msa.hub.domain.model.Hub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository extends JpaRepository<Hub, String> {
    boolean existsByName(String name);
}
