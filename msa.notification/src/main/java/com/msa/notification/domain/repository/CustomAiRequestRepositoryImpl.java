package com.msa.notification.domain.repository;

import com.msa.notification.applicaiton.dto.AiMessageResponse;
import com.msa.notification.applicaiton.dto.QAiMessageResponse;
import com.msa.notification.domain.QAiRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CustomAiRequestRepositoryImpl implements CustomAiRequestRepository {

    private final JPAQueryFactory jpa;

    @Override
    public Page<AiMessageResponse> getAiRequestList(Pageable pageable) {

        QAiRequest qAiRequest = QAiRequest.aiRequest;

        List<AiMessageResponse> content = jpa
                .select(new QAiMessageResponse(
                        qAiRequest.id,
                        qAiRequest.requestData,
                        qAiRequest.answer
                ))
                .from(qAiRequest)
                .where(isNotDeleted())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(jpa
                .select(qAiRequest.count())
                .from(qAiRequest)
                .where(isNotDeleted())
                .fetchOne()
        ).orElse(0L);

        return null;
    }

    private BooleanExpression isNotDeleted() {
        return QAiRequest.aiRequest.isDeleted.eq(false);
    }
}
