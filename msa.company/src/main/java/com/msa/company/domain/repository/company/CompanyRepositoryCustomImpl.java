package com.msa.company.domain.repository.company;

import com.msa.company.domain.model.Company;
import com.msa.company.domain.model.QCompany;
import com.msa.company.domain.model.enums.CompanyStatus;
import com.msa.company.domain.model.enums.CompanyType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Company> getListCompanies(String hubId, String name, String type, String status, String address, Pageable pageable) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // 필터링 조건 추가
        if (hubId != null) {
            booleanBuilder.and(QCompany.company.hubId.eq(UUID.fromString(hubId)));
        }
        if (name != null) {
            booleanBuilder.and(QCompany.company.name.like("%" + name + "%"));
        }
        if (type != null) {
            booleanBuilder.and(QCompany.company.type.eq(CompanyType.valueOf(type)));
        }
        if (status != null) {
            booleanBuilder.and(QCompany.company.status.eq(CompanyStatus.valueOf(status)));
        }
        if (address != null) {
            booleanBuilder.and(
                    QCompany.company.address.city.likeIgnoreCase("%" + address + "%")
                            .or(QCompany.company.address.district.likeIgnoreCase("%" + address + "%"))
                            .or(QCompany.company.address.streetName.likeIgnoreCase("%" + address + "%"))
                            .or(QCompany.company.address.streetNumber.likeIgnoreCase("%" + address + "%"))
                            .or(QCompany.company.address.addressDetail.likeIgnoreCase("%" + address + "%"))
            );
        }

        // 정렬 기준 설정
        OrderSpecifier<?>[] orderSpecifiers = getOrderSpecifiers(pageable);

        // 쿼리 작성
        List<Company> results = queryFactory.selectFrom(QCompany.company)
                .where(booleanBuilder)
                .orderBy(orderSpecifiers)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 총 카운트 쿼리 작성
        JPAQuery<Long> countQuery = queryFactory.select(QCompany.company.count())
                .from(QCompany.company)
                .where(booleanBuilder);

        // 페이징 처리
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchOne);
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
            case "updatedAt" -> QCompany.company.updatedAt;
            case "hubId" -> QCompany.company.hubId;
            case "id" -> QCompany.company.id;
            default -> QCompany.company.createdAt;
        };
    }
}