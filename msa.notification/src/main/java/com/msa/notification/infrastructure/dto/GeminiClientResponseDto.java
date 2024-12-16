package com.msa.notification.infrastructure.dto;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record GeminiClientResponseDto(
        List<Candidate> candidates
) {

    @Getter
    @NoArgsConstructor
    public static class Candidate {
        private Content content;
    }

    @Getter
    @NoArgsConstructor
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Getter
    @NoArgsConstructor
    public static class Part {
        private String text;
    }

}
