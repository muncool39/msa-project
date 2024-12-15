package com.msa.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.msa.delivery.domain.entity.Delivery;

public interface DeliveryRepositoryCustom {

	Optional<Delivery> findByIdAndIsDeletedFalse(UUID id);
}
