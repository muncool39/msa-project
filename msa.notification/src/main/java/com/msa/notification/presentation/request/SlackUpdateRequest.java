package com.msa.notification.presentation.request;

import com.msa.notification.applicaiton.dto.UpdateSlackRequest;
import jakarta.validation.constraints.NotBlank;

public record SlackUpdateRequest(
        @NotBlank
        String message
) {

    public UpdateSlackRequest toDTO() {
        return new UpdateSlackRequest(this.message);
    }
}
