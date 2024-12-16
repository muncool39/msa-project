package com.msa.order.infrastructure.config.feign;

import org.springframework.stereotype.Component;

import com.msa.order.exception.BusinessException.CustomBadRequestException;
import com.msa.order.exception.BusinessException.CustomFeignException;
import com.msa.order.exception.BusinessException.CustomForbiddenException;
import com.msa.order.exception.BusinessException.NotFoundException;
import com.msa.order.exception.BusinessException.UnauthorizedException;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomErrorDecoder implements ErrorDecoder {
	@Override
	public Exception decode(String methodKey, Response response) {

		log.error("Feign error occurred - methodKey: {}, url: {}, status: {}", methodKey, response.request().url(), response.status());

		String errorPrefix = "";
		if (methodKey.contains("DeliveryClient")) {
			errorPrefix = "[배송서비스] ";
		} else if (methodKey.contains("HubClient")) {
			errorPrefix = "[결제서비스] ";
		} else if (methodKey.contains("ProductClient")) {
			errorPrefix = "[상품서비스] ";
		} else if (methodKey.contains("UserClient")) {
			errorPrefix = "[유저서비스] ";
		}

		return switch (response.status()) {
			case 400 -> new CustomBadRequestException(errorPrefix);
			case 401 -> new UnauthorizedException(errorPrefix);
			case 403 -> new CustomForbiddenException(errorPrefix);
			case 404 -> new NotFoundException(errorPrefix);
			case 500 -> new CustomFeignException(errorPrefix);
			default -> new RuntimeException(errorPrefix + "Unknown External Service error");
		};
	}
}
