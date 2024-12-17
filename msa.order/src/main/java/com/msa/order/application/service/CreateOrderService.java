package com.msa.order.application.service;

import com.msa.order.application.client.DeliveryManager;
import com.msa.order.application.client.ProductManager;
import com.msa.order.application.client.UserManager;
import com.msa.order.application.client.dto.CompanyData;
import com.msa.order.application.client.dto.CreateDeliveryRequest;
import com.msa.order.application.client.dto.DeliveryData;
import com.msa.order.application.client.dto.ProductData;
import com.msa.order.application.client.dto.ProductStockRequest;
import com.msa.order.application.client.dto.UserData;
import com.msa.order.domain.entity.Order;
import com.msa.order.domain.repository.OrderRepository;
import com.msa.order.exception.BusinessException.FeignException;
import com.msa.order.exception.BusinessException.OrderException;
import com.msa.order.exception.ErrorCode;
import com.msa.order.presentation.request.CreateOrderRequest;
import com.msa.order.presentation.response.ApiResponse;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	// TODO 유저,상품,배송 서비스 연동 전 임시 하드 코딩
	final UUID deliveryId = UUID.randomUUID();

	@Transactional
	public void createOrder(CreateOrderRequest request) {
		ProductData productData = reduceProductStock(request);
		CompanyData receiveCompanyData = getCompanyData(request.receiveCompanyId());
		UserData receiveCompanyManagerData = getUserData(receiveCompanyData);
		Order savedOrder = saveOrder(request, productData);
		CreateDeliveryRequest deliveryRequest = createDeliveryRequest(productData, savedOrder, receiveCompanyData, receiveCompanyManagerData);
		createDeliveryAndUpdateOrder(deliveryRequest, savedOrder);
	}

	private ProductData reduceProductStock(CreateOrderRequest request) {
		ApiResponse<ProductData> response = productManager.reduceStock(request.itemId(),
		    new ProductStockRequest(request.quantity()));
		ProductData productData = response.data();
		if (productData.productId() == null) {
		  throw new OrderException(ErrorCode.STOCK_REDUCTION_FAILED);
		}

		return productData;
	}

	private CompanyData getCompanyData(UUID receiveCompanyId) {
		ApiResponse<CompanyData> response = productManager.getCompanyInfo(receiveCompanyId);
		CompanyData companyData = response.data();
		if (companyData.id() == null) {
			throw new FeignException(ErrorCode.COMPANY_SERVICE_ERROR);
		}
		return companyData;
	}

	private UserData getUserData(CompanyData receiveCompanyData) {
		ApiResponse<UserData> response = userManager.getUserInfo(receiveCompanyData.userId());
		UserData userData = response.data();
		if (userData.id() == null) {
			throw new FeignException(ErrorCode.USER_SERVICE_ERROR);
		}
		return userData;
	}

	private Order saveOrder(CreateOrderRequest request, ProductData productData) {

		Order order = Order.create(productData.companyId(), request.receiveCompanyId(),
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
			throw new FeignException(ErrorCode.DELIVERY_SERVICE_ERROR);
		}
		savedOrder.updateDeliveryId(deliveryData.companyDeliverId());
	}







}