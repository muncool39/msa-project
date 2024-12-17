package com.msa.order.application.client;

import com.msa.order.application.client.dto.response.UserData;
import com.msa.order.presentation.response.ApiResponse;

public interface UserManager {
	ApiResponse<UserData> getUserInfo(Long userId);
}