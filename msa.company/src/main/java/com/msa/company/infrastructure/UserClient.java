package com.msa.company.infrastructure;

import com.msa.company.config.FeignConfig;
import com.msa.company.presentation.response.ApiResponse;
import com.msa.company.presentation.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service",
        configuration = FeignConfig.class)
public interface UserClient {

    @GetMapping("/users/{userId}")
    ApiResponse<UserResponse> findUser(@PathVariable("userId") Long userId);
}
