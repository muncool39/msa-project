package com.msa.notification.infrastructure;

public record SlackClientRequestDto(
         String channel,
         String text
) {
    public static SlackClientRequestDto of(String receiptId, String message) {
        return new SlackClientRequestDto(receiptId, message);
    }
}
