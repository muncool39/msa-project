package com.msa.user.domain.repository;

import static com.msa.user.domain.model.QUser.user;

import com.msa.user.common.exception.ErrorCode;
import com.msa.user.common.exception.UserException;
import com.msa.user.domain.model.Role;
import com.msa.user.domain.model.User;
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
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<User> findUsersWith(
            Pageable pageable, String username, Role role, String belongHudId, String belongCompanyId
    ) {
        BooleanBuilder booleanBuilder = getBooleanBuilder(username, role, belongHudId, belongCompanyId);
        List<User> results = queryFactory.selectFrom(user)
                .where(booleanBuilder)
                .orderBy(getOrderSpecifiers(pageable))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> count = queryFactory.select(user.count())
                .from(user)
                .where(booleanBuilder);
        return PageableExecutionUtils.getPage(results, pageable, count::fetchOne);
    }

    private BooleanBuilder getBooleanBuilder(
            String username, Role role, String belongHudId, String belongCompanyId
    ) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(likeUsername(username));
        builder.and(eqRole(role));
        builder.and(eqBelongHubId(belongHudId));
        builder.and(eqBelongCompanyId(belongCompanyId));
        builder.and(user.isDeleted.eq(false));
        return builder;
    }

    private BooleanExpression likeUsername(String username) {
        return user.username.like("%" + ((username!=null) ? username  : "") + "%");
    }

    private BooleanExpression eqRole(Role role) {
        return role != null ? user.role.eq(role) : null;
    }

    private BooleanExpression eqBelongHubId(String belongHudId) {
        return belongHudId != null ? user.belongHubId.eq(belongHudId) : null;
    }

    private BooleanExpression eqBelongCompanyId(String belongCompanyId) {
        return belongCompanyId != null ? user.belongCompanyId.eq(belongCompanyId) : null;
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
    User 정렬 기준
     */
    private ComparableExpressionBase<?> getSortPath(String property) {
        return switch (property) {
            case "username" -> user.username;
            case "createdAt" -> user.createdAt;
            case "updatedAt" -> user.updatedAt;
            default -> throw new UserException(ErrorCode.UNSUPPORTED_SORT_TYPE);
        };
    }

}
