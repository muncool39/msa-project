package com.msa.notification.applicaiton.dto;

import com.msa.notification.domain.SlackNotification;

public record DeleteSlackResponse(
        String slackNotificationId,
        String slackRecipientId,
        boolean isDeleted
) {
    public static DeleteSlackResponse fromEntity(SlackNotification slackNotification) {
        return new DeleteSlackResponse(
                slackNotification.getId().toString(),
                slackNotification.getSlackRecipientId(),
                slackNotification.isDeleted()
        );
    }
}
