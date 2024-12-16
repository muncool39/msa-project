package com.msa.order.infrastructure.client.fallback;

import org.springframework.stereotype.Component;

import com.msa.order.application.client.dto.response.UserData;
import com.msa.order.infrastructure.client.impl.UserClient;
import com.msa.order.presentation.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "User-Client")
@Component("userClientFallback")
public class UserClientFallback implements UserClient {

  @Override
  public ApiResponse<UserData> getUserInfo(Long userId) {
    log.error("[FeignClient] 유저 호출 서비스 에러 발생 ");
    return null;
  }
}
