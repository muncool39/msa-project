package com.msa.notification.presentation;

import com.msa.notification.applicaiton.SlackService;
import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import com.msa.notification.presentation.request.SlackCreateRequest;
import com.msa.notification.presentation.request.SlackUpdateRequest;
import com.msa.notification.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/slack-notifications")
public class SlackNotificationController {

    private final SlackService slackService;

    @PostMapping

    public ApiResponse<SlackNotificationResponse> sendSlackNotification(
            @RequestBody @Valid SlackCreateRequest request) {
        return ApiResponse.success(slackService.createSlackMessage(request.toDTO()));
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @GetMapping("/{slackRecipientId}")
    public ApiResponse<SlackNotificationResponse> getSlackNotification(
            @PathVariable UUID slackRecipientId) {
        return ApiResponse.success(slackService.getSlackMessage(slackRecipientId));
    }


    @PreAuthorize("hasAuthority('MASTER')")
    @PatchMapping("/{slackNotificationId}")
    public ApiResponse<SlackNotificationResponse> updateSlackNotification(
            @PathVariable UUID slackNotificationId,
            @RequestBody @Valid SlackUpdateRequest request) {
        return ApiResponse.success(
                slackService.updateSlackMessage(slackNotificationId, request.toDTO()));
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @GetMapping("/{slackRecipientId}/list")
    public ApiResponse<PagedModel<SlackNotificationResponse>> listSlackNotification(
            @RequestParam(required = false) String slackRecipientId,
            Pageable pageable) {
        Page<SlackNotificationResponse> listSlackMessageResponse = slackService.getListSlackMessage(
                slackRecipientId, pageable);
        return ApiResponse.success(new PagedModel<>(listSlackMessageResponse));
    }

    @PreAuthorize("hasAuthority('MASTER')")
    @DeleteMapping("/{slackNotificationId}")
    public ApiResponse<SlackNotificationResponse> deleteSlackNotification(
            @PathVariable UUID slackNotificationId) {
        return ApiResponse.success(slackService.deleteSlackMessage(slackNotificationId));
    }

}
