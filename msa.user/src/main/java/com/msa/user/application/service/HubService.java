package com.msa.user.application.service;

import org.springframework.web.bind.annotation.RequestParam;

public interface HubService {
    Boolean verifyHub(@RequestParam String hubId);
    Boolean postManager(@RequestParam String hubId, @RequestParam Long userId);
}
