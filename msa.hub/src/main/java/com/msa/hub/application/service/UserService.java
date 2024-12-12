package com.msa.hub.application.service;

import com.msa.hub.application.dto.UserDetailResponse;
import com.msa.hub.presentation.response.ApiResponse;
import org.springframework.web.bind.annotation.PathVariable;

public interface UserService {
    ApiResponse<UserDetailResponse> findUser(@PathVariable Long userId);
}
