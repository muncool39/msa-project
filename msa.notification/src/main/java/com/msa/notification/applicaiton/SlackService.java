package com.msa.notification.applicaiton;

import com.msa.notification.applicaiton.dto.SlackNotificationRequest;
import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import com.msa.notification.domain.SlackNotification;
import com.msa.notification.infrastructure.SlackClientRequestDto;
import com.msa.notification.domain.repository.SlackNotificationRepository;
import lombok.RequiredArgsConstructor;
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

}
