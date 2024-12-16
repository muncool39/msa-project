package com.msa.notification.domain.repository;

import com.msa.notification.applicaiton.dto.AiMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class AiRequestRepositoryCustomImpl implements
        AiRequestRepositoryCustom {
    @Override
    public Page<AiMessageResponse> searchAiMessages(String username, Pageable pageable) {
        return null;
    }
}
