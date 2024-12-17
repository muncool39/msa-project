package com.msa.user.presentation.controller;

import com.msa.user.application.dto.DeleteShipperResponse;
import com.msa.user.application.dto.ShipperAssignResponseDto;
import com.msa.user.application.dto.ShipperResponse;
import com.msa.user.application.service.ShipperService;
import com.msa.user.presentation.request.CreateShipperRequest;
import com.msa.user.presentation.request.ShipperAssignRequest;
import com.msa.user.presentation.request.UpdateShipperRequest;
import com.msa.user.presentation.response.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ApiResponse<ShipperResponse> createShipper(
            @Valid @RequestBody CreateShipperRequest request,
            @AuthenticationPrincipal Long userId) {
        return ApiResponse.success(shipperService.createShipper(request, userId));
    }

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    @PatchMapping("/{shipperId}")
    public ApiResponse<ShipperResponse> updateShipper(@PathVariable Long shipperId,
                                                      @Valid @RequestBody UpdateShipperRequest request
    ) {
        return ApiResponse.success(shipperService.updateShipper(shipperId, request));
    }


    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER','DELIVERY_MANAGER')")
    @GetMapping("/{shipperId}")
    public ApiResponse<ShipperResponse> getShipper(@PathVariable Long shipperId) {
        return ApiResponse.success(shipperService.getShipper(shipperId));
    }

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER','DELIVERY_MANAGER')")
    @GetMapping
    public ApiResponse<List<ShipperResponse>> getShipperList() {
        return ApiResponse.success(shipperService.getShippers());
    }

    @PreAuthorize("hasAnyRole('MASTER', 'HUB_MANAGER')")
    @DeleteMapping
    public ApiResponse<DeleteShipperResponse> deleteShipper(Long shipperId) {
        return ApiResponse.success(shipperService.deleteShipper(shipperId));
    }

    @PostMapping("/assign")
    public ApiResponse<ShipperAssignResponseDto> assignShippers(
            @Valid @RequestBody ShipperAssignRequest request) {
        System.out.println("assignShippers");
        return ApiResponse.success(shipperService.assignShippers(request));
    }

}
