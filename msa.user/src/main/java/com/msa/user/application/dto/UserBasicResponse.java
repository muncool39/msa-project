package com.msa.user.application.dto;

import com.msa.user.domain.model.Role;
import com.msa.user.domain.model.User;

public record UserBasicResponse(
        Long userId,
        String username,
        Role role,
        String belongHubId,
        String belongCompanyId
) {
    public static UserBasicResponse toUserPageResponse(User user) {
        return new UserBasicResponse(
                user.getId(),
                user.getUsername(),
                user.getRole(),
                user.getBelongHubId(),
                user.getBelongCompanyId()
        );
    }
}
