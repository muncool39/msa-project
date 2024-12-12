package com.msa.user.application.dto;

import com.msa.user.domain.model.Role;
import com.msa.user.domain.model.User;
import java.time.LocalDateTime;

public record UserDetailResponse(
        Long id,
        String username,
        String email,
        String slackId,
        Role role,
        LocalDateTime createAt,
        LocalDateTime updatedAt
) {
    public static UserDetailResponse from(User user) {
        return new UserDetailResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getSlackId(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
