package com.msa.delivery.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.QDelivery;
import com.msa.delivery.domain.entity.QDeliveryRouteHistory;
import com.msa.delivery.domain.entity.enums.DeliveryStatus;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryRepositoryCustomImpl implements DeliveryRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QDelivery delivery = QDelivery.delivery;
	private final QDeliveryRouteHistory deliveryRouteHistory = QDeliveryRouteHistory.deliveryRouteHistory;

	@Override
	public Optional<Delivery> findByIdAndIsDeletedFalse(UUID id) {
		BooleanBuilder builder = new BooleanBuilder();
		builder
			.and(delivery.isDeleted.eq(false))
			.and(delivery.id.eq(id));

		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(delivery)
				.join(delivery.deliveryHistories).fetchJoin()
				.where(builder)
				.fetchOne()
		);
	}

	@Override
	public Page<Delivery> searchDeliveries(Pageable pageable, String search) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(delivery.isDeleted.eq(false));

		searchCondition(search, builder);

		long totalCount = getTotalCount(delivery, builder);

		List<Delivery> results = jpaQueryFactory
			.selectFrom(delivery)
			.join(delivery.deliveryHistories, deliveryRouteHistory).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, delivery))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Delivery> searchDeliveriesByHubId(Pageable pageable, String search, UUID hubId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder
			.and(delivery.isDeleted.eq(false))
			.and(delivery.departureHubId.eq(hubId)
				.or(delivery.destinationHubId.eq(hubId))
				.or(deliveryRouteHistory.departureHubId.eq(hubId))
				.or(deliveryRouteHistory.destinationHubId.eq(hubId))
			);

		searchCondition(search, builder);

		long totalCount = getTotalCount(delivery, builder);

		List<Delivery> results = jpaQueryFactory
			.selectFrom(delivery)
			.join(delivery.deliveryHistories, deliveryRouteHistory).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, delivery))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Delivery> searchDeliveriesByHubDeliverId(Pageable pageable, String search, Long userId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder
			.and(delivery.isDeleted.eq(false))
			.and(deliveryRouteHistory.deliverId.eq(userId));

		searchCondition(search, builder);

		long totalCount = getTotalCount(delivery, builder);

		List<Delivery> results = jpaQueryFactory
			.selectFrom(delivery)
			.join(delivery.deliveryHistories, deliveryRouteHistory).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, delivery))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Delivery> searchDeliveriesByCompanyDeliveryId(Pageable pageable, String search, Long userId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder
			.and(delivery.isDeleted.eq(false))
			.and(delivery.deliverId.eq(userId));

		searchCondition(search, builder);

		long totalCount = getTotalCount(delivery, builder);

		List<Delivery> results = jpaQueryFactory
			.selectFrom(delivery)
			.join(delivery.deliveryHistories, deliveryRouteHistory).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, delivery))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	@Override
	public Page<Delivery> searchDeliveriesByReceiveCompanyId(Pageable pageable, String search, UUID companyId) {
		BooleanBuilder builder = new BooleanBuilder();
		builder
			.and(delivery.isDeleted.eq(false))
			.and(delivery.receiveCompanyId.eq(companyId));

		searchCondition(search, builder);

		long totalCount = getTotalCount(delivery, builder);

		List<Delivery> results = jpaQueryFactory
			.selectFrom(delivery)
			.join(delivery.deliveryHistories, deliveryRouteHistory).fetchJoin()
			.where(builder)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(getOrderSpecifier(pageable, delivery))
			.fetch();

		return new PageImpl<>(results, pageable, totalCount);
	}

	private long getTotalCount(QDelivery delivery, BooleanBuilder builder) {
		return jpaQueryFactory
			.selectFrom(delivery)
			.join(delivery.deliveryHistories, deliveryRouteHistory)
			.where(builder)
			.fetchCount();
	}

	private OrderSpecifier<?>[] getOrderSpecifier(Pageable pageable, QDelivery delivery) {
		List<OrderSpecifier> orderSpecifiers = new ArrayList<>();

		if (pageable.getSort().isSorted()) {
			Sort.Order sortOrder = pageable.getSort().iterator().next();
			switch (sortOrder.getProperty()) {
				case "createdAt" -> {
					orderSpecifiers.add(new OrderSpecifier(Order.DESC, delivery.createdAt));
				}
				case "updatedAt" -> {
					orderSpecifiers.add(new OrderSpecifier(Order.ASC, delivery.updatedAt));
				}
			}
		}

		if (orderSpecifiers.isEmpty()) {
			orderSpecifiers.add(new OrderSpecifier(Order.DESC, delivery.createdAt));
		}

		return orderSpecifiers.toArray(new OrderSpecifier[0]);
	}

	private void searchCondition(String search, BooleanBuilder builder) {
		if (StringUtils.hasText(search)) {
			try {
				UUID searchUUID = UUID.fromString(search);
				builder.and(
					delivery.orderId.eq(searchUUID)
						.or(delivery.receiveCompanyId.eq(searchUUID))
						.or(delivery.departureHubId.eq(searchUUID))
						.or(delivery.destinationHubId.eq(searchUUID))
						.or(delivery.receiveCompanyId.eq(searchUUID))
						.or(deliveryRouteHistory.departureHubId.eq(searchUUID))
						.or(deliveryRouteHistory.destinationHubId.eq(searchUUID))
				);

			} catch (IllegalArgumentException ignored) {
				try {
					Long searchLong = Long.parseLong(search);
					builder.and(deliveryRouteHistory.deliverId.eq(searchLong));
				} catch (NumberFormatException ignored2) {
					try {
						DeliveryStatus searchStatus = DeliveryStatus.valueOf(search.toUpperCase());
						builder.and(
							delivery.status.eq(searchStatus)
								.or(deliveryRouteHistory.status.eq(searchStatus))
						);
					} catch (IllegalArgumentException ignored3) {
						builder.and(
							delivery.address.city.contains(search)
								.or(delivery.address.district.contains(search))
								.or(delivery.address.streetname.contains(search))
								.or(delivery.receiverName.contains(search))
								.or(delivery.receiverSlackId.contains(search))
						);
					}
				}
			}
		}
	}
}
