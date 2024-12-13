package com.msa.order.domain.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.msa.order.domain.entity.Order;

import feign.Param;

public interface OrderRepositoryCustom {

	Page<Order> searchOrders(Pageable pageable, @Param("search") String search);

	Page<Order> searchOrdersByDepartureHubId(Pageable pageable, @Param("search") String search, UUID hubId);

	Page<Order> searchOrdersByDeliveryId(Pageable pageable, @Param("search") String search, Long userId);

	Page<Order> searchOrdersByReceiveCompanyId(Pageable pageable, @Param("search") String search, UUID companyId);
}
