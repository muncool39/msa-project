package com.msa.order.infrastructure.config.jpa;

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

		if (authentication.getName().equals("anonymousUser")) {
			return Optional.of("anonymousUser");
		}

		return Optional.of(authentication.getName());
	}
}


