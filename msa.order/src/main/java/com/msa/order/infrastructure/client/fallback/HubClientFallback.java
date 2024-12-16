package com.msa.order.infrastructure.client.fallback;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.msa.order.application.client.dto.response.HubData;
import com.msa.order.infrastructure.client.impl.HubClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "Hub-Client")
@Component("hubFallback")
public class HubClientFallback implements HubClient {

	@Override
	public HubData getHubInfo(UUID hubId) {
		return null;
	}
}
