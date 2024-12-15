package com.msa.notification.applicaiton;

import static com.msa.notification.exception.ErrorCode.*;

import com.msa.notification.applicaiton.dto.DeleteSlackResponse;
import com.msa.notification.applicaiton.dto.SlackNotificationRequest;
import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import com.msa.notification.applicaiton.dto.UpdateSlackRequest;
import com.msa.notification.domain.SlackNotification;
import com.msa.notification.exception.businessException.NotificationApiException;
import com.msa.notification.infrastructure.SlackClientRequestDto;
import com.msa.notification.domain.repository.SlackNotificationRepository;
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

        validateDeleted(slackNotification);
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

        validateDeleted(slackNotification);

        slackNotification.updateMessage(request.message());
        slackNotification.updateHistory(slackNotification.getSlackRecipientId());

        return SlackNotificationResponse.fromEntity(slackNotification);
    }

    @Transactional
    public DeleteSlackResponse deleteSlackMessage(UUID slackNotificationId) {

        SlackNotification slackNotification = slackNotificationRepository.findById(
                        slackNotificationId)
                .orElseThrow(() -> new NotificationApiException(NOT_FOUND_SLACK_NOTIFICATION));

        validateDeleted(slackNotification);

        slackNotification.updateDeletedHistory(slackNotification.getSlackRecipientId());

        return DeleteSlackResponse.fromEntity(slackNotification);
    }

    private void validateDeleted(SlackNotification slackNotification) {
        if (slackNotification.isDeleted()) {
            throw new NotificationApiException(DELETED_SLACK_HISTORY);
        }
    }

}
