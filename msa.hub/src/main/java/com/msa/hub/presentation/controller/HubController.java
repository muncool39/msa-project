package com.msa.hub.presentation.controller;

import com.msa.hub.application.dto.HubDetailResponse;
import com.msa.hub.application.service.HubService;
import com.msa.hub.presentation.request.HubCreateRequest;
import com.msa.hub.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hubs")
@RequiredArgsConstructor
public class HubController {

    private final HubService hubService;

    @PostMapping
    @PreAuthorize("hasAuthority('MASTER')")
    public ApiResponse<Void> postHub(
            @Valid @RequestBody HubCreateRequest request
    ) {
        hubService.createHub(request);
        return ApiResponse.success();
    }

    @GetMapping("/{hubId}")
    public ApiResponse<HubDetailResponse> findHub(
            @PathVariable String hubId
    ) {
        return ApiResponse.success(hubService.getHubDetail(hubId));
    }

    @PostMapping("/manager")
    public Boolean postManager(
            @RequestParam(required = true) String hubId,
            @RequestParam(required = true) Long userId
    ) {
        hubService.updateHubManager(hubId, userId);
        return true;
    }

    @GetMapping("/verify")
    public Boolean verifyHub(@RequestParam String hubId) {
        hubService.getHubDetail(hubId);
        return true;
    }
}
