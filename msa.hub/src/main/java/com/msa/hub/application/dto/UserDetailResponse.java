package com.msa.hub.application.dto;

import java.time.LocalDateTime;

public record UserDetailResponse(
        Long id,
        String username,
        String email,
        String slackId,
        Role role,
        String belongHubId,
        LocalDateTime createAt,
        LocalDateTime updatedAt
) {
}
