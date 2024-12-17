package com.msa.user.presentation.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ShipperAssignRequestDto(
        @NotNull
        List<PathDto> paths
) {
    public static record PathDto(
            @NotBlank
            String nodeId,

            @NotNull
            Integer sequence,

            @NotBlank
            String departureHubId,

            @NotBlank
            String destinationHubId
    ) {
    }
}
