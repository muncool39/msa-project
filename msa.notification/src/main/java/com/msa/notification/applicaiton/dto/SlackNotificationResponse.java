package com.msa.notification.applicaiton.dto;

import com.msa.notification.domain.SlackNotification;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.UUID;

public record SlackNotificationResponse(
        UUID id,
        String slackRecipientId,
        String message,
        LocalDateTime sentAt
) {

    public static SlackNotificationResponse fromEntity(SlackNotification slackNotification) {
        return new SlackNotificationResponse(
                slackNotification.getId(),
                slackNotification.getSlackRecipientId(),
                slackNotification.getMessage(),
                slackNotification.getSentAt());
    }
    @QueryProjection
    public SlackNotificationResponse(
            UUID id, String slackRecipientId, String message, LocalDateTime sentAt) {
        this.id = id;
        this.slackRecipientId = slackRecipientId;
        this.message = message;
        this.sentAt = sentAt;
    }
}
