package com.msa.notification.domain.repository;

import com.msa.notification.applicaiton.dto.QSlackNotificationResponse;
import com.msa.notification.applicaiton.dto.SlackNotificationResponse;
import com.msa.notification.domain.QSlackNotification;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class CustomSlackRepositoryImpl implements CustomSlackRepository {

    private final JPAQueryFactory jpa;

    @Override
    public Page<SlackNotificationResponse> searchSlackNotifications(String slackRecipientId,
                                                                Pageable pageable) {

        QSlackNotification slackNotification = QSlackNotification.slackNotification;

        List<SlackNotificationResponse> content = jpa
                .select(new QSlackNotificationResponse(
                        slackNotification.id,
                        slackNotification.slackRecipientId,
                        slackNotification.message,
                        slackNotification.sentAt
                ))
                .from(slackNotification)
                .where(
                        slackRecipientIdContains(slackRecipientId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = Optional.ofNullable(jpa
                .select(slackNotification.count())
                .from(slackNotification)
                .where(
                        slackRecipientIdContains(slackRecipientId)
                )
                .fetchOne()
        ).orElse(0L);

        return null;

    }

    private BooleanExpression slackRecipientIdContains(String slackRecipientId) {
        return slackRecipientId != null ? QSlackNotification.
                slackNotification.slackRecipientId.contains(slackRecipientId) : null;
    }

//    private BooleanExpression isNotDeleted() {
//        return QSlackNotification.slackNotification.de.eq(false);
//    }

}
