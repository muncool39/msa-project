package com.msa.notification.applicaiton;

import com.msa.notification.infrastructure.SlackClientRequestDto;

public interface SlackClientService {
    void sendMessage(SlackClientRequestDto requestDto);
}
