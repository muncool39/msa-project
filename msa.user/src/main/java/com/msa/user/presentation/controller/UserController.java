package com.msa.user.presentation.controller;

import com.msa.user.application.dto.UserDetailResponse;
import com.msa.user.application.dto.UserBasicResponse;
import com.msa.user.application.service.UserService;
import com.msa.user.domain.model.Role;
import com.msa.user.presentation.request.SetBelongHubRequest;
import com.msa.user.presentation.request.UserUpdateRequest;
import com.msa.user.presentation.response.ApiResponse;
import com.msa.user.presentation.response.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    @PreAuthorize("hasAuthority('MASTER')")
    public ApiResponse<PageResponse<UserBasicResponse>> getUser(
            @PageableDefault(size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "username", direction = Direction.ASC),
                    @SortDefault(sort = "createdAt", direction = Direction.DESC),
                    @SortDefault(sort = "updatedAt", direction = Direction.DESC)
            })
            Pageable pageable,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Role role,
            @RequestParam(required = false) String belongHudId,
            @RequestParam(required = false) String belongCompanyId
    ) {
        return ApiResponse.success(PageResponse.of(
                userService.findUsers(pageable, username, role, belongHudId, belongCompanyId))
        );
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

    @DeleteMapping("/belong-hub")
    public Boolean detachBelongHub(
            @RequestParam Long userId
    ) {
        userService.hubDetach(userId);
        return true;
    }

    @DeleteMapping
    public ApiResponse<Void> deleteUser(
            Authentication authentication
    ) {
        userService.deleteUser(Long.valueOf(authentication.getName()));
        return ApiResponse.success();
    }

}
