package com.msa.notification.applicaiton.dto;

import com.msa.notification.domain.AiRequest;
import java.util.UUID;

public record DeleteAiMessageResponse(
        UUID aiRequestId,
        boolean isDeleted
) {
    public static DeleteAiMessageResponse fromEntity(AiRequest aiRequest) {
        return new DeleteAiMessageResponse(
                aiRequest.getId(),
                aiRequest.isDeleted()
        );
    }
}
