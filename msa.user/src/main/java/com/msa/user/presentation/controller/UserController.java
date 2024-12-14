package com.msa.user.presentation.controller;

import com.msa.user.application.dto.UserDetailResponse;
import com.msa.user.application.service.UserService;
import com.msa.user.presentation.request.SetBelongHubRequest;
import com.msa.user.presentation.request.UserUpdateRequest;
import com.msa.user.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @PatchMapping
    public ApiResponse<Void> updateUser(
            Authentication authentication,
            @NotNull @RequestBody UserUpdateRequest request
    ) {
        userService.updateUser(Long.valueOf(authentication.getName()), request);
        return ApiResponse.success();
    }

    @PostMapping("/belong-hub")
    @PreAuthorize("hasAuthority('MASTER')")
    public ApiResponse<Void> postUserBelongHub(
            @Valid @RequestBody SetBelongHubRequest request
    ) {
        userService.setBelongHub(request.userId(), request.hubId());
        return ApiResponse.success();
    }

}
