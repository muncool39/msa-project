package com.msa.user.presentation.controller;

import com.msa.user.application.service.AuthService;
import com.msa.user.presentation.request.SignInRequest;
import com.msa.user.presentation.request.SignUpRequest;
import com.msa.user.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ApiResponse<Void> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        authService.createUser(signUpRequest);
        return ApiResponse.success();
    }

    @PostMapping("/sign-in")
    public Map<String, String> singIn(
            @Valid @RequestBody SignInRequest signInRequest
    ) {
        return Map.of("token", authService.createAccessToken(signInRequest));
    }

}
