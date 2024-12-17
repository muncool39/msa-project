package com.msa.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.msa.delivery.domain.entity.Delivery;

import feign.Param;

public interface DeliveryRepositoryCustom {

	Optional<Delivery> findByIdAndIsDeletedFalse(UUID id);

	Page<Delivery> searchDeliveries(Pageable pageable, @Param("search") String search);

	Page<Delivery> searchDeliveriesByHubId(Pageable pageable, String search, UUID hubId);

	Page<Delivery> searchDeliveriesByHubDeliverId(Pageable pageable, String search, Long userId);

	Page<Delivery> searchDeliveriesByCompanyDeliveryId(Pageable pageable, String search, Long userId);

	Page<Delivery> searchDeliveriesByReceiveCompanyId(Pageable pageable, String search, UUID companyId);
}
