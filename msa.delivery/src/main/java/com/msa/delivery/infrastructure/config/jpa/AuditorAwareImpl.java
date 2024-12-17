package com.msa.delivery.infrastructure.config.jpa;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (null == authentication || !authentication.isAuthenticated()) {
			return Optional.empty();
		}

		String username = authentication.getName();
		if (username == null) {
			return Optional.of("SYSTEM"); // 서버 통신의 경우 발생
		}

		if (authentication.getName().equals("anonymousUser")) {
			return Optional.of("anonymousUser");
		}

		return Optional.of(authentication.getName());
	}
}


