package com.msa.order.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msa.order.domain.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID>, OrderRepositoryCustom {

  Optional<Order> findByIdAndIsDeletedFalse(UUID id);

  Page<Order> findAllByIsDeletedFalse(Pageable pageable);

}
