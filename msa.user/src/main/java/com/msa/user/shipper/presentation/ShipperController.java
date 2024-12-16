package com.msa.user.shipper.presentation;

import com.msa.user.shipper.application.dto.DeleteShipperResponse;
import com.msa.user.shipper.application.dto.ShipperResponse;
import com.msa.user.shipper.application.ShipperService;
import com.msa.user.shipper.presentation.request.CreateShipperRequest;
import com.msa.user.shipper.presentation.request.UpdateShipperRequest;
import com.msa.user.presentation.response.ApiResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shippers")
public class ShipperController {

    private final ShipperService shipperService;

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    @PostMapping
    public ApiResponse<ShipperResponse> createShipper(@RequestBody CreateShipperRequest request) {
        return ApiResponse.success(shipperService.createShipper(request));
    }

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    @PatchMapping("/{shipperId}")
    public ApiResponse<ShipperResponse> updateShipper(@PathVariable UUID shipperId,
                                                      @RequestBody UpdateShipperRequest request
    ) {
        return ApiResponse.success(shipperService.updateShipper(shipperId, request));
    }


    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER','DELIVERY_MANAGER')")
    @GetMapping("/{shipperId}")
    public ApiResponse<ShipperResponse> getShipper(UUID shipperId) {
        return ApiResponse.success(shipperService.getShipper(shipperId));
    }

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER','DELIVERY_MANAGER')")
    @GetMapping
    public ApiResponse<List<ShipperResponse>> getShipperList() {
        return ApiResponse.success(shipperService.getShippers());
    }

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    @DeleteMapping
    public ApiResponse<DeleteShipperResponse> deleteShipper(UUID shipperId) {
        return ApiResponse.success(shipperService.deleteShipper(shipperId));
    }


}
