package com.msa.notification.applicaiton;

import com.msa.notification.infrastructure.dto.GeminiClientRequestDto;
import com.msa.notification.infrastructure.dto.GeminiClientResponseDto;

public interface GeminiClientService {
    GeminiClientResponseDto sendPrompt(String key, GeminiClientRequestDto requestDto);

}
