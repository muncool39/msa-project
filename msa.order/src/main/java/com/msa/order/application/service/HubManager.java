package com.msa.order.application.service;

import java.util.UUID;

import com.msa.order.application.service.dto.HubData;

public interface HubManager {

	HubData getHubInfo(UUID hubId);
}
