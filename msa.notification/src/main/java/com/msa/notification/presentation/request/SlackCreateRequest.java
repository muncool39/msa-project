package com.msa.notification.presentation.request;

import com.msa.notification.applicaiton.dto.SlackNotificationRequest;
import jakarta.validation.constraints.NotBlank;

public record SlackCreateRequest(
        @NotBlank
        String slackRecipientId,
        @NotBlank
        String message
) {
    public SlackNotificationRequest toDTO() {
        return new SlackNotificationRequest(this.slackRecipientId, this.message);
    }
}