package com.msa.notification.applicaiton;

import static com.msa.notification.exception.ErrorCode.*;

import com.msa.notification.applicaiton.dto.SlackNotificationRequest;
import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import com.msa.notification.applicaiton.dto.UpdateSlackRequest;
import com.msa.notification.domain.SlackNotification;
import com.msa.notification.exception.ErrorCode;
import com.msa.notification.exception.businessException.NotificationApiException;
import com.msa.notification.infrastructure.SlackClientRequestDto;
import com.msa.notification.domain.repository.SlackNotificationRepository;
import com.slack.api.methods.SlackApiException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SlackService {

    private final SlackNotificationRepository slackNotificationRepository;
    private final SlackClientService slackClientService;

    @Transactional
    public SlackNotificationResponse createSlackMessage(SlackNotificationRequest request) {
        slackClientService.sendMessage(
                SlackClientRequestDto.of(request.slackRecipientId(), request.message()));

        SlackNotification slackNotification = SlackNotification.builder()
                .slackRecipientId(request.slackRecipientId())
                .message(request.message())
                .build();

        slackNotificationRepository.save(slackNotification);

        return SlackNotificationResponse.fromEntity(slackNotification);
    }

    public SlackNotificationResponse getSlackMessage(UUID slackNotificationId) {
        SlackNotification slackNotification = slackNotificationRepository.findById(
                        slackNotificationId)
                .orElseThrow(() -> new NotificationApiException(NOT_FOUND_SLACK_NOTIFICATION));

        return SlackNotificationResponse.fromEntity(slackNotification);
    }

    public Page<SlackNotificationResponse> getListSlackMessage(String slackRecipientId,
                                                               Pageable pageable) {
        return slackNotificationRepository.searchSlackNotifications(slackRecipientId, pageable);
    }

    @Transactional
    public SlackNotificationResponse updateSlackMessage(UUID slackNotificationId,
                                                        UpdateSlackRequest request) {

        SlackNotification slackNotification = slackNotificationRepository.findById(
                        slackNotificationId)
                .orElseThrow(() -> new NotificationApiException(NOT_FOUND_SLACK_NOTIFICATION));

        slackNotification.updateMessage(request.message());

        return SlackNotificationResponse.fromEntity(slackNotification);
    }

    @Transactional
    public SlackNotificationResponse deleteSlackMessage(UUID slackNotificationId) {

        SlackNotification slackNotification = slackNotificationRepository.findById(
                        slackNotificationId)
                .orElseThrow(() -> new NotificationApiException(NOT_FOUND_SLACK_NOTIFICATION));

        //TODO: BaseEntity 적용 후 소프트 델리트로 수정
        slackNotificationRepository.delete(slackNotification);

        return SlackNotificationResponse.fromEntity(slackNotification);
    }


}
