package com.msa.notification.domain.repository;

import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomSlackRepository {
    Page<SlackNotificationResponse> searchSlackNotifications(String slackRecipientId, Pageable pageable);

}
