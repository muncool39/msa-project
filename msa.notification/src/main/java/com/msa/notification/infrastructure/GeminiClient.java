package com.msa.notification.infrastructure;

import com.msa.notification.applicaiton.GeminiClientService;
import com.msa.notification.infrastructure.dto.GeminiClientRequestDto;
import com.msa.notification.infrastructure.dto.GeminiClientResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "slackClient", url = "${service.gemini.request-url}")
public interface GeminiClient extends GeminiClientService {

    @PostMapping
    GeminiClientResponseDto sendPrompt(
            @RequestParam("key") String key,
            @RequestBody GeminiClientRequestDto requestDto);

}
