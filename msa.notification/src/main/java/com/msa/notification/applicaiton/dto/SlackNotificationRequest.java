package com.msa.notification.applicaiton.dto;

public record SlackNotificationRequest (
        String slackRecipientId,
        String message
){
}
