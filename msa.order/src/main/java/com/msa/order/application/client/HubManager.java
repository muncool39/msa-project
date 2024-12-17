package com.msa.order.application.client;

import java.util.UUID;

import com.msa.order.application.client.dto.response.HubData;

public interface HubManager {

	HubData getHubInfo(UUID hubId);
}
