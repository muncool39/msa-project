package com.msa.order.application.client.dto;

import java.util.UUID;

public record CompanyData(
    UUID id,
    String name,
    UUID hubId
) {

}
