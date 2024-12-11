package com.msa.hub.presentation.controller;

import com.msa.hub.application.service.HubService;
import com.msa.hub.presentation.request.HubCreateRequest;
import com.msa.hub.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/verify")
    public Boolean verifyHub(@RequestParam(value = "hub_id") String hubId) {
        return true;
    }
}
