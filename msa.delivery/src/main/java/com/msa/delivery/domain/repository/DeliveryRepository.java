package com.msa.delivery.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msa.delivery.domain.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
	Optional<Delivery> findByOrderId(UUID orderId);
}
