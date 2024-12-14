package com.msa.company.infrastructure;

import com.msa.company.config.FeignConfig;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "hub-service",
        configuration = FeignConfig.class)
public interface HubClient {

    @GetMapping("/hubs/verify")
    Boolean isHubExists(@RequestParam("hub_id") UUID hubId);
}
