package com.msa.delivery.infrastructure.client.fallback;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import com.msa.delivery.infrastructure.client.impl.UserClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserClientFallbackFactory implements FallbackFactory<UserClient> {

	@Override
	public UserClient create(Throwable cause) {
		log.error("[FeignClient] user-service 에러 발생. 원인: {}", cause.getMessage());
		return new UserClientFallback();
	}
}
