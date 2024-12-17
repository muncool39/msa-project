package com.msa.company.infrastructure;

import com.msa.company.infrastructure.config.FeignConfig;
import com.msa.company.application.dto.response.ApiResponse;
import com.msa.company.application.dto.response.HubResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "hub-service",
        configuration = FeignConfig.class)
public interface HubClient {

    @GetMapping("/hubs/{hubId}")
    ApiResponse<HubResponse> findHub(@PathVariable("hubId") String hubId);
}
