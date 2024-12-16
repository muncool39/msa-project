package com.msa.hub.domain.repository;


import static com.msa.hub.domain.model.QHub.hub;

import com.msa.hub.common.exception.ErrorCode;
import com.msa.hub.common.exception.HubException;
import com.msa.hub.domain.model.Hub;
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
public class HubRepositoryImpl implements HubCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Hub> findHubsWith(
            Pageable pageable, String name, String city, String district, String streetName
    ) {
        BooleanBuilder booleanBuilder = getBooleanBuilder(name, city, district, streetName);
        List<Hub> results = queryFactory.selectFrom(hub)
                .where(booleanBuilder)
                .orderBy(getOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> count = queryFactory.select(hub.count())
                .from(hub)
                .where(booleanBuilder);
        return PageableExecutionUtils.getPage(results, pageable, count::fetchOne);
    }

    private BooleanBuilder getBooleanBuilder(
            String name, String city, String district, String streetName
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(likeName(name));
        builder.and(likeCityName(city));
        builder.and(likeDistrictName(district));
        builder.and(likeStreetName(streetName));
        builder.and(hub.isDeleted.eq(false));
        return builder;
    }

    private BooleanExpression likeName(String name) {
        return hub.name.like("%" + ((name!=null) ? name  : "") + "%");
    }

    private BooleanExpression likeCityName(String city) {
        return hub.city.like("%" + ((city!=null) ? city  : "") + "%");
    }

    private BooleanExpression likeDistrictName(String district) {
        return hub.district.like("%" + ((district!=null) ? district  : "") + "%");
    }

    private BooleanExpression likeStreetName(String streetName) {
        return hub.streetName.like("%" + ((streetName!=null) ? streetName  : "") + "%");
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
    Hub 정렬 기준
     */
    private ComparableExpressionBase<?> getSortPath(String property) {
        return switch (property) {
            case "name" -> hub.name;
            case "city" -> hub.city;
            case "district" -> hub.district;
            case "createdAt" -> hub.createdAt;
            case "updatedAt" -> hub.updatedAt;
            default -> throw new HubException(ErrorCode.UNSUPPORTED_SORT_TYPE);
        };
    }
}
