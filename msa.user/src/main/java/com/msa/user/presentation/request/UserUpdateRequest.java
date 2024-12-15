package com.msa.user.presentation.request;

public record UserUpdateRequest(
        String username,
        String password,
        String email,
        String slackId
) {
}
