package com.msa.order.domain.repository;

import static com.querydsl.core.types.Order.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.msa.order.domain.entity.Order;
import com.msa.order.domain.entity.QOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	private final QOrder order = QOrder.order;

	@Override
	public Page<Order> searchOrders(Pageable pageable, String search) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(order.isDeleted.eq(false));

		if (StringUtils.hasText(search)) {
			builder.and(
					order.address.city.contains(search)
				.or(order.address.district.contains(search))
				.or(order.address.streetname.contains(search))
				.or(order.address.streetnum.contains(search))
				.or(order.address.detail.contains(search))
				.or(order.itemName.contains(search))
			);
		}

		long totalCount = getTotalCount(order, builder);

		List<Order> results = jpaQueryFactory
			.selectFrom(order)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, order))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Order> searchOrdersByDepartureHubId(Pageable pageable, String search, UUID hubId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(order.isDeleted.eq(false));
		builder.and(order.departureHubId.eq(hubId));

		if (StringUtils.hasText(search)) {
			builder.and(
				order.address.city.contains(search)
					.or(order.address.district.contains(search))
					.or(order.address.streetname.contains(search))
					.or(order.address.streetnum.contains(search))
					.or(order.address.detail.contains(search))
					.or(order.itemName.contains(search))
			);
		}

		long totalCount = getTotalCount(order, builder);

		List<Order> results = jpaQueryFactory
			.selectFrom(order)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, order))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Order> searchOrdersByDeliveryId(Pageable pageable, String search, Long userId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(order.isDeleted.eq(false));
		builder.and(order.deliveryId.eq(userId));

		if (StringUtils.hasText(search)) {
			builder.and(
				order.address.city.contains(search)
					.or(order.address.district.contains(search))
					.or(order.address.streetname.contains(search))
					.or(order.address.streetnum.contains(search))
					.or(order.address.detail.contains(search))
					.or(order.itemName.contains(search))
			);
		}

		long totalCount = getTotalCount(order, builder);

		List<Order> results = jpaQueryFactory
			.selectFrom(order)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, order))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Order> searchOrdersByReceiverCompanyId(Pageable pageable, String search, UUID companyId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(order.isDeleted.eq(false));
		builder.and(order.receiverCompanyId.eq(companyId));

		if (StringUtils.hasText(search)) {
			builder.and(
				order.address.city.contains(search)
					.or(order.address.district.contains(search))
					.or(order.address.streetname.contains(search))
					.or(order.address.streetnum.contains(search))
					.or(order.address.detail.contains(search))
					.or(order.itemName.contains(search))
			);
		}

		long totalCount = getTotalCount(order, builder);

		List<Order> results = jpaQueryFactory
			.selectFrom(order)
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, order))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	private long getTotalCount(QOrder order, BooleanBuilder builder) {
		return jpaQueryFactory
			.selectFrom(order)
			.where(builder)
			.fetchCount();
	}


	private OrderSpecifier<?>[] getOrderSpecifier(Pageable pageable, QOrder order) {
		List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

		if (pageable.getSort().isSorted()) {
			Sort.Order sortOrder = pageable.getSort().iterator().next();
			switch (sortOrder.getProperty()) {
				case "createdAt":
					orderSpecifiers.add(new OrderSpecifier(DESC, order.createdAt));
					break;
				case "updatedAt":
					orderSpecifiers.add(new OrderSpecifier(ASC, order.updatedAt));
					break;
			}
		}

		// 기본 정렬 조건
		if (orderSpecifiers.isEmpty()) {
			orderSpecifiers.add(new OrderSpecifier(DESC, order.createdAt));
		}

		return orderSpecifiers.toArray(new OrderSpecifier[0]);
	}



}
