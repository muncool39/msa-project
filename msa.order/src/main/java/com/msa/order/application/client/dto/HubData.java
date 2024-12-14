package com.msa.order.application.client.dto;

public record HubData(
	String name,
	String city,
	String district,
	String streetName,
	String streetNumber,
	String addressDetail,
	Double latitude,
	Double longitude,
	Long managerId // TODO 타입 확인 Long or String
) {

}
