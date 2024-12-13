package com.msa.user.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SetBelongHubRequest(
        @NotNull Long userId,
        @NotBlank String hubId
) {
}
