package com.msa.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.entity.QDelivery;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeliveryRepositoryCustomImpl implements DeliveryRepositoryCustom {

	private final JPAQueryFactory jpaQueryFactory;
	private final QDelivery delivery = QDelivery.delivery;

	@Override
	public Optional<Delivery> findByIdAndIsDeletedFalse(UUID id) {
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(delivery.isDeleted.eq(false))
			.and(delivery.id.eq(id));

		return Optional.ofNullable(
			jpaQueryFactory.selectFrom(delivery)
				.leftJoin(delivery.deliveryHistories).fetchJoin()
				.where(builder)
				.fetchOne()
		);
	}

}
