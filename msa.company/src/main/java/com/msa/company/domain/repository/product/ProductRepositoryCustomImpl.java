package com.msa.company.domain.repository.product;

import com.msa.company.domain.model.Product;
import com.msa.company.domain.entity.QProduct;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    // 업체 내 상품 조회
    @Override
    public Page<Product> findByCompanyId(UUID companyId, String name, String isOutOfStock, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 쿼리 설정
        JPAQuery<Product> query = queryFactory.selectFrom(QProduct.product)
                .where(QProduct.product.company.id.eq(companyId));

        // 검색 조건 추가
        if (name != null && !name.isEmpty()) {
            builder.and(QProduct.product.name.like("%" + name + "%"));
        }
        if (isOutOfStock != null && !isOutOfStock.isEmpty()) {
            builder.and(QProduct.product.isOutOfStock.eq(Boolean.valueOf(isOutOfStock)));
        }

        // 쿼리에 조건 추가
        query.where(builder);

        // 페이징 처리
        List<Product> products = getPagedResult(query, pageable);

        Long totalCount = queryFactory.select(QProduct.product.count())
                .from(QProduct.product)
                .where(builder)
                .fetchOne();

        // fetchOne() = null -> totalCount = 0
        totalCount = totalCount != null ? totalCount : 0L;

        return new PageImpl<>(products, pageable, totalCount);
    }

    // 전체 상품 조회
    public Page<Product> getListProducts(String companyId, String companyName, String name, String isOutOfStock, Pageable pageable) {
        BooleanBuilder builder = new BooleanBuilder();

        if (companyId != null && !companyId.isEmpty()) {
            builder.and(QProduct.product.company.id.eq(UUID.fromString(companyId)));
        }
        if (companyName != null && !companyName.isEmpty()) {
            builder.and(QProduct.product.company.name.like("%" + companyName + "%"));
        }
        if (name != null && !name.isEmpty()) {
            builder.and(QProduct.product.name.like("%" + name + "%"));
        }
        if (isOutOfStock != null && !isOutOfStock.isEmpty()) {
            builder.and(QProduct.product.isOutOfStock.eq(Boolean.valueOf(isOutOfStock)));
        }

        // 정렬 기준 설정
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable);

        // 쿼리 작성
        List<Product> results = queryFactory.selectFrom(QProduct.product)
                .where(builder)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 카운트 쿼리 작성
        JPAQuery<Long> countQuery = queryFactory.select(QProduct.product.count())
                .from(QProduct.product)
                .where(builder);

        // 페이징 처리
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
    }

    private List<Product> getPagedResult(JPAQuery<Product> query, Pageable pageable) {
        return query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    // 정렬 기준 처리
    private OrderSpecifier<?>[] getOrderSpecifiers(Pageable pageable) {
        return pageable.getSort().stream()
                .map(order -> {
                    ComparableExpressionBase<?> sortPath = getSortPath(order.getProperty());
                    return new OrderSpecifier<>(
                            order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                            sortPath);
                })
                .toArray(OrderSpecifier[]::new);
    }

    // 정렬 기준을 필드별로 매핑하는 메서드
    private ComparableExpressionBase<?> getSortPath(String property) {
        return switch (property) {
            case "updatedAt" -> QProduct.product.updatedAt;
            case "id" -> QProduct.product.id;
            default -> QProduct.product.createdAt;
        };
    }
}
