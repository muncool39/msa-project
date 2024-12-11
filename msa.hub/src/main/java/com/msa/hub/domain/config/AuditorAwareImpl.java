package com.msa.hub.domain.config;

import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (null == authentication || !authentication.isAuthenticated()) {
            return Optional.empty();
        }
        log.info("authentication.getName(): {}", authentication.getName());
        if (authentication.getName().equals("anonymousUser")) {
            return Optional.of("anonymousUser");
        }
        return Optional.of(authentication.getName());
    }
}
