package com.msa.order.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {
		return switch (response.status()) {
			case 400 -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request in feignClient request");
			case 403 -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden in feignClient request");
			default -> new RuntimeException("Unknown error");
		};
	}
}
