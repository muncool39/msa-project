package com.msa.order.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msa.order.application.client.DeliveryManager;
import com.msa.order.application.client.ProductManager;
import com.msa.order.application.client.UserManager;
import com.msa.order.application.client.dto.request.CreateDeliveryRequest;
import com.msa.order.application.client.dto.request.ProductStockRequest;
import com.msa.order.application.client.dto.response.CompanyData;
import com.msa.order.application.client.dto.response.DeliveryData;
import com.msa.order.application.client.dto.response.ProductData;
import com.msa.order.application.client.dto.response.UserData;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.CustomFeignException;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.ErrorCode;
import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CreateOrderService {

	private final OrderRepository orderRepository;
	private final ProductManager productManager;
	private final DeliveryManager deliveryManager;
	private final UserManager userManager;

	public CreateOrderService(OrderRepository orderRepository,
		@Qualifier("productClient") ProductManager productManager,
		@Qualifier("deliveryClient") DeliveryManager deliveryManager,
		@Qualifier("userClient") UserManager userManager) {
		this.orderRepository = orderRepository;
		this.productManager = productManager;
		this.deliveryManager = deliveryManager;
		this.userManager = userManager;
	}

	@Transactional
	public void createOrder(CreateOrderRequest request) {
		ProductData productData = reduceProductStock(request);
		CompanyData receiveCompanyData = getCompanyData(request.receiverCompanyId());
		UserData receiveCompanyManagerData = getUserData(receiveCompanyData);
		Order savedOrder = saveOrder(request, productData);
		CreateDeliveryRequest deliveryRequest = createDeliveryRequest(productData, savedOrder, receiveCompanyData, receiveCompanyManagerData);
		createDeliveryAndUpdateOrder(deliveryRequest, savedOrder);
	}

	private ProductData reduceProductStock(CreateOrderRequest request) {
		ApiResponse<ProductData> response = productManager.reduceStock(request.itemId(),
		    new ProductStockRequest(request.quantity()));
		return response.data();
	}

	private CompanyData getCompanyData(UUID receiverCompanyId) {
		ApiResponse<CompanyData> response = productManager.getCompanyInfo(receiverCompanyId);
		return response.data();
	}

	private UserData getUserData(CompanyData receiveCompanyData) {
		ApiResponse<UserData> response = userManager.getUserInfo(receiveCompanyData.userId());
		return response.data();
	}

	private Order saveOrder(CreateOrderRequest request, ProductData productData) {

		Order order = Order.create(productData.companyId(), request.receiverCompanyId(),
			request.itemId(), request.itemName(), request.quantity(), request.description(),
			request.city(), request.district(), request.streetName(), request.streetNum(), request.detail(),
			productData.hubId());

		return orderRepository.save(order);
	}

	private CreateDeliveryRequest createDeliveryRequest(ProductData productData, Order savedOrder,
		CompanyData companyData, UserData receiveCompanyManagerData) {
		return new CreateDeliveryRequest(savedOrder.getId(), receiveCompanyManagerData.username(), receiveCompanyManagerData.slackId(),
			savedOrder.getAddress(), companyData.id(), productData.hubId(), receiveCompanyManagerData.belongHubId());
	}

	private void createDeliveryAndUpdateOrder(CreateDeliveryRequest deliveryRequest, Order savedOrder) {
		ApiResponse<DeliveryData> response = deliveryManager.createDelivery(deliveryRequest);
		DeliveryData deliveryData = response.data();
		if (deliveryData.deliveryId() == null) {
			throw new CustomFeignException();
		}
		savedOrder.updateDeliveryId(deliveryData.companyDeliverId());
	}







}