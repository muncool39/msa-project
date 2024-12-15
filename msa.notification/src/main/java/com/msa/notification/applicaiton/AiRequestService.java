package com.msa.notification.applicaiton;

import static com.msa.notification.exception.ErrorCode.NOT_FOUND_AI_REQUEST;

import com.msa.notification.applicaiton.dto.AiMessageRequest;
import com.msa.notification.applicaiton.dto.AiMessageResponse;
import com.msa.notification.domain.AiRequest;
import com.msa.notification.domain.repository.AiRequestRepository;
import com.msa.notification.exception.businessException.AiRequestApiException;
import com.msa.notification.infrastructure.dto.GeminiClientRequestDto;
import com.msa.notification.infrastructure.dto.GeminiClientResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AiRequestService {
    private static final String FORMATTING_MESSAGE = ", 위 내용을 기반으로 몇월 며칠 오전/오후 몇시까지 형식으로 최종 발송 시한을 도출해줘";
    private static final String MAX_LENGTH_PROMPT_MESSAGE = ", 답변을 최대한 간결하게 50자 이하로 작성해.";

    private final AiRequestRepository aiRequestRepository;
    private final GeminiClientService geminiClientService;

    @Value("${service.gemini.api-key}")
    private String apiKey;

    @Transactional
    public AiMessageResponse createAiRequest(AiMessageRequest request) {

        GeminiClientResponseDto clientResponseDto = geminiClientService.sendPrompt(apiKey,
                GeminiClientRequestDto.create(
                        request.requestData() +
                                FORMATTING_MESSAGE,
                        MAX_LENGTH_PROMPT_MESSAGE));

        AiRequest aiRequest = AiRequest.builder()
                .requestData(request.requestData())
                .answer(clientResponseDto.candidates()
                        .get(0)
                        .getContent()
                        .getParts()
                        .get(0)
                        .getText())
                .build();

        aiRequestRepository.save(aiRequest);

        return AiMessageResponse.fromEntity(aiRequest);
    }

    public AiMessageResponse getAiRequest(UUID id) {
        AiRequest aiRequest = aiRequestRepository.findById(id)
                .orElseThrow(() -> new AiRequestApiException(NOT_FOUND_AI_REQUEST));
        return AiMessageResponse
                .fromEntity(aiRequest);
    }

    public Page<AiMessageResponse> getAiRequestList(Pageable pageable) {
        return aiRequestRepository.getAiRequestList(pageable);
    }


}
