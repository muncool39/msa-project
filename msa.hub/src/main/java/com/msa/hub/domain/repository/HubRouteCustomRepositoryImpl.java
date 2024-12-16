package com.msa.hub.domain.repository;





import static com.msa.hub.domain.model.QHubRoute.hubRoute;

import com.msa.hub.domain.model.HubRoute;
import com.msa.hub.common.exception.ErrorCode;
import com.msa.hub.common.exception.HubException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class HubRouteCustomRepositoryImpl implements HubRouteCustomRepository {

    private final JPAQueryFactory queryFactory;
    
    @Override
    public Page<HubRoute> findHubRoutesWith(
            Pageable pageable, String sourceHubId, String destinationHubId
    ) {
        BooleanBuilder booleanBuilder = toBooleanBuilder(sourceHubId, destinationHubId);
        List<HubRoute> result = queryFactory.selectFrom(hubRoute)
                .where(booleanBuilder)
                .orderBy(getOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> count = queryFactory.select(hubRoute.count())
                .from(hubRoute)
                .where(booleanBuilder);
        return PageableExecutionUtils.getPage(result, pageable, count::fetchOne);

    }

    private BooleanBuilder toBooleanBuilder(String sourceHubId, String destinationHubId) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(eqDestinationHub(destinationHubId));
        builder.and(eqSourceHub(sourceHubId));
        //builder.and(hubRoute.isDeleted.eq(false));
        return builder;
    }

    private BooleanExpression eqSourceHub(String sourceHubId) {
        return sourceHubId != null ? hubRoute.sourceHub.id.eq(sourceHubId) : null;
    }

    private BooleanExpression eqDestinationHub(String destinationHubId) {
        return destinationHubId != null ? hubRoute.destinationHub.id.eq(destinationHubId) : null;
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        return pageable.getSort().stream()
                .map(order -> {
                    ComparableExpressionBase<?> sortPath = getSortPath(order.getProperty());
                    return new OrderSpecifier<>(
                            order.isAscending()
                                    ? com.querydsl.core.types.Order.ASC
                                    : com.querydsl.core.types.Order.DESC,
                            sortPath);
                })
                .toArray(OrderSpecifier[]::new);
    }

    /*
    HubRoute 정렬 기준 저장
     */
    private ComparableExpressionBase<?> getSortPath(String property) {
        return switch (property) {
            case "createdAt" -> hubRoute.createdAt;
            case "updatedAt" -> hubRoute.updatedAt;
            default -> throw new HubException(ErrorCode.UNSUPPORTED_SORT_TYPE);
        };
    }

}
