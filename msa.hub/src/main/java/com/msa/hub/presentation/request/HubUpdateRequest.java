package com.msa.hub.presentation.request;

public record HubUpdateRequest(
        String name,
        String city,
        String district,
        String streetName,
        String streetNumber,
        String addressDetail,
        Double latitude,
        Double longitude
) {
}
