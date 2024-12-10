package com.msa.user.presentation.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
