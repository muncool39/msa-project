package com.msa.user.presentation.controller;

import com.msa.user.application.dto.UserDetailResponse;
import com.msa.user.application.service.UserService;
import com.msa.user.presentation.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ApiResponse<UserDetailResponse> findUser(
            @PathVariable Long userId
    ) {
        return ApiResponse.success(userService.getUser(userId));
    }

}
