package com.msa.notification.applicaiton.dto;

import java.util.UUID;

public record AiMessageCreateResponse(
        UUID aiRequestId,
        String answer
) {

}
