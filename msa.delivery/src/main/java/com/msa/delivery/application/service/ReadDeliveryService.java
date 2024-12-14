package com.msa.delivery.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.msa.delivery.domain.entity.Delivery;
import com.msa.delivery.domain.repository.DeliveryRepository;
import com.msa.delivery.exception.BusinessException.DeliveryNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReadDeliveryService {

	private final DeliveryRepository deliveryRepository;

	public Delivery getDeliveryByOrderId(UUID orderId) {

		return deliveryRepository.findByOrderId(orderId)
			.orElseThrow(DeliveryNotFoundException::new);
	}
}
