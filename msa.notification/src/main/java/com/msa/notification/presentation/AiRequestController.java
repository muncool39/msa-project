package com.msa.notification.presentation;

import com.msa.notification.applicaiton.AiRequestService;
import com.msa.notification.applicaiton.dto.AiMessageRequest;
import com.msa.notification.applicaiton.dto.AiMessageResponse;
import com.msa.notification.presentation.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ai-requests")
public class AiRequestController {

    private final AiRequestService aiRequestService;

    @PreAuthorize("hasRole('MASTER')")
    @PostMapping
    public ApiResponse<AiMessageResponse> createAiRequest(AiMessageRequest request) {
        return ApiResponse.success(aiRequestService.createAiRequest(request));
    }

}
