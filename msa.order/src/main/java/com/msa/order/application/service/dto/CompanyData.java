package com.msa.order.application.service.dto;

import java.util.UUID;

public record CompanyData(
    UUID id,
    String name,
    UUID hubId
) {

}
