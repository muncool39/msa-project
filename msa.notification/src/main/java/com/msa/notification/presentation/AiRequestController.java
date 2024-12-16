package com.msa.notification.presentation;

import com.msa.notification.applicaiton.AiRequestService;
import com.msa.notification.applicaiton.dto.AiMessageRequest;
import com.msa.notification.applicaiton.dto.AiMessageResponse;
import com.msa.notification.applicaiton.dto.DeleteAiMessageResponse;
import com.msa.notification.presentation.response.ApiResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.data.web.config.SpringDataJacksonConfiguration.PageModule;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/{aiRequestId}")
    public ApiResponse<AiMessageResponse> getAiRequest(@PathVariable UUID aiRequestId) {
        return ApiResponse.success(aiRequestService.getAiRequest(aiRequestId));
    }

    @PreAuthorize("hasRole('MASTER')")
    @GetMapping("/list")
    public ApiResponse<PagedModel<AiMessageResponse>> getAiRequestList(Pageable pageable) {
        return ApiResponse.success(new PagedModel<>(aiRequestService.getAiRequestList(pageable)));
    }

    @PreAuthorize("hasRole('MASTER')")
    @DeleteMapping("/{aiRequestId}")
    public ApiResponse<DeleteAiMessageResponse> deleteAiRequest(@PathVariable UUID aiRequestId) {
        return ApiResponse.success(aiRequestService.deleteAiRequest(aiRequestId));
    }
}
