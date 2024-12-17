package com.msa.user.infrastructure;

import com.msa.user.application.service.HubService;
import com.msa.user.presentation.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "hub-service")
public interface HubClient extends HubService {

    @GetMapping("/hubs/verify")
    Boolean verifyHub(@RequestParam("hubId") String hubId);

    @PostMapping("/hubs/manager")
    Boolean postManager(
            @RequestParam("hubId") String hubId,
            @RequestParam("userId") Long userId
    );
}
