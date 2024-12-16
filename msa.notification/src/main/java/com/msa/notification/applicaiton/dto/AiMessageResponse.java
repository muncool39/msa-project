package com.msa.notification.applicaiton.dto;

import com.msa.notification.domain.AiRequest;
import com.querydsl.core.annotations.QueryProjection;
import java.util.UUID;

public record AiMessageResponse(
        UUID aiRequestId,
        String requestData,
        String answer
) {


    public static AiMessageResponse fromEntity(AiRequest aiRequest) {
        return new AiMessageResponse(
                aiRequest.getId(),
                aiRequest.getRequestData(),
                aiRequest.getAnswer());
    }

    @QueryProjection
    public AiMessageResponse(UUID aiRequestId, String requestData, String answer) {
        this.aiRequestId = aiRequestId;
        this.requestData = requestData;
        this.answer = answer;
    }
}
