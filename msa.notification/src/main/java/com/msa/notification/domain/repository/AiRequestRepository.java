package com.msa.notification.domain.repository;

import com.msa.notification.domain.AiRequest;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiRequestRepository extends JpaRepository<AiRequest, UUID>,
        CustomAiRequestRepository {
}
