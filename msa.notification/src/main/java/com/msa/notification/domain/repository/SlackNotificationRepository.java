package com.msa.notification.domain.repository;

import com.msa.notification.domain.SlackNotification;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlackNotificationRepository extends JpaRepository<SlackNotification, UUID>,
        CustomSlackRepository {

}
