package com.msa.order.infrastructure;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.order.application.service.dto.HubData;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Hub-Client")
@Component("hubFallback")
public class HubClientFallback implements HubClient {

	@Override
	public HubData getHubInfo(UUID hubId) {
		return null;
	}
}
