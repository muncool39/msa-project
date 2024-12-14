package com.msa.notification.presentation;

import com.msa.notification.applicaiton.SlackService;
import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import com.msa.notification.presentation.request.SlackCreateRequest;
import com.msa.notification.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class SlackNotificationController {

    private final SlackService slackService;

    @PostMapping("/slack-notifications")
    public ApiResponse<SlackNotificationResponse> sendSlackNotification(
            @RequestBody @Valid SlackCreateRequest request) {
        return ApiResponse.success(slackService.createSlackMessage(request.toDTO()));
    }

}
