package com.msa.notification.presentation.request;

import com.msa.notification.applicaiton.dto.AiMessageRequest;
import jakarta.validation.constraints.NotBlank;

public record AiMessageCreateRequest(
        @NotBlank
        String requestData
) {

    public AiMessageRequest toDTO() {
        return new AiMessageRequest(this.requestData);
    }
}
