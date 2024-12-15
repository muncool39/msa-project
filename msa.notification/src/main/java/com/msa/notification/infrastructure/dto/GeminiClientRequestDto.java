package com.msa.notification.infrastructure.dto;

import java.util.Collections;
import java.util.List;
import lombok.Getter;

public record GeminiClientRequestDto(
        List<Content> contents
) {
        public record Content(List<Part> parts) {
    }

        public record Part(String text) {
    }

    public static GeminiClientRequestDto create(String prompt, String maxLengthPromptMessage) {
        Part part = new Part(prompt + maxLengthPromptMessage);
        Content content = new Content(Collections.singletonList(part));

        return new GeminiClientRequestDto(Collections.singletonList(content));
    }
}
