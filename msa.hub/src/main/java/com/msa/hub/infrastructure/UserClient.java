package com.msa.hub.infrastructure;


import com.msa.hub.application.dto.UserDetailResponse;
import com.msa.hub.application.service.UserService;
import com.msa.hub.presentation.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service")
public interface UserClient extends UserService {
    @GetMapping("/users/{userId}")
    ApiResponse<UserDetailResponse> findUser(@PathVariable Long userId);
}
