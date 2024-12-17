package com.msa.delivery.infrastructure.config.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class FeignConfiguration {


	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {

			// TODO 배송 담당자 서비스 권한 문제 해결시 반영
			// if (requestTemplate.url().contains("/shippers")) {
			// 	requestTemplate.header("X-User-Role", "MASTER");
			// } else {
			// 	ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			// 	if (attributes != null) {
			// 		HttpServletRequest request = attributes.getRequest();
			// 		final String id = request.getHeader("X-User-Id");
			// 		final String role = request.getHeader("X-User-Role");
			// 		requestTemplate.header("X-User-Id", id);
			// 		requestTemplate.header("X-User-Role", role);
			// 	}
			// }

			ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
			if (attributes != null) {
				HttpServletRequest request = attributes.getRequest();
				final String id = request.getHeader("X-User-Id");
				final String role = request.getHeader("X-User-Role");
				requestTemplate.header("X-User-Id", id);
				requestTemplate.header("X-User-Role", role);
			}

		};
	}

	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
}
