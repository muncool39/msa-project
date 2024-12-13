package com.msa.hub.presentation.controller;


import com.msa.hub.application.service.HubRouteCreateService;
import com.msa.hub.presentation.request.HubRouteCreateRequest;
import com.msa.hub.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("hub-routes")
public class HubRouteController {

    private final HubRouteCreateService hubRouteCreateService;

    @PostMapping
    @PreAuthorize("hasAuthority('MASTER')")
    public ApiResponse<Void> postHubRoute(
            @Valid @RequestBody HubRouteCreateRequest request
    ) {
        hubRouteCreateService.createRoute(request.sourceHubId(), request.destinationHubId());
        return ApiResponse.success();
    }
}
