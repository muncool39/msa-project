package com.msa.delivery.application.client.dto;

import java.util.UUID;

import com.msa.delivery.domain.entity.enums.UserRole;

public record UserData(
	Long id,
	String username,
	String email,
	String slackId,
	UserRole role,
	String type, // TODO 명세 확인 필요) 배송담당자의 타입
	UUID belongHubId,
	UUID belongCompanyId
) {

}
