package com.msa.order.application.client;

import com.msa.order.application.client.dto.UserData;

public interface UserManager {
	UserData getUserInfo(Long userId);
}