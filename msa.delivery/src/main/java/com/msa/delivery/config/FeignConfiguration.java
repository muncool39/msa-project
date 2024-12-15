package com.msa.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import okhttp3.OkHttpClient;

@Configuration
public class FeignConfiguration {

	@Bean
	public OkHttpClient client() {
		return new OkHttpClient();
	}

	@Bean
	public RequestInterceptor requestInterceptor() {
		return requestTemplate -> {
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
}
