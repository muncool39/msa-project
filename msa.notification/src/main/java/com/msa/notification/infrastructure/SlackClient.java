package com.msa.notification.infrastructure;

import com.msa.notification.applicaiton.SlackClientService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "slack", url = "${service.slack.request-url}")
public interface SlackClient extends SlackClientService {

    @PostMapping
    void sendMessage(SlackClientRequestDto requestDto);
}
