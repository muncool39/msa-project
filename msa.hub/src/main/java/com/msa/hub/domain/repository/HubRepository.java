package com.msa.hub.domain.repository;

import com.msa.hub.domain.model.Hub;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HubRepository extends JpaRepository<Hub, String> {
    Optional<Hub> findByIdAndIsDeleted(String id, boolean isDeleted);
    boolean existsByName(String name);
}
