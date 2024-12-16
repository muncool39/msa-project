package com.msa.notification.domain.repository;

import com.msa.notification.applicaiton.dto.AiMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAiRequestRepository {
    Page<AiMessageResponse> getAiRequestList(Pageable pageable);

}
