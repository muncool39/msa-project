package com.msa.hub.presentation.controller;


import com.msa.hub.application.dto.HubRouteDetailResponse;
import com.msa.hub.application.dto.HubRouteResponse;
import com.msa.hub.application.service.HubRouteCreateService;
import com.msa.hub.application.service.HubRouteReadService;
import com.msa.hub.presentation.request.HubRouteCreateRequest;
import com.msa.hub.presentation.response.ApiResponse;
import com.msa.hub.presentation.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hub-routes")
public class HubRouteController {

    private final HubRouteCreateService hubRouteCreateService;
    private final HubRouteReadService hubRouteReadService;

    @PostMapping
    @PreAuthorize("hasAuthority('MASTER')")
    public ApiResponse<Void> postHubRoute(
            @Valid @RequestBody HubRouteCreateRequest request
    ) {
        hubRouteCreateService.createRoute(request.sourceHubId(), request.destinationHubId());
        return ApiResponse.success();
    }
    @GetMapping
    public ApiResponse<PageResponse<HubRouteResponse>> getHubRoutes(
            @PageableDefault(size = 10)
            @SortDefault.SortDefaults({
                    @SortDefault(sort = "createdAt", direction = Direction.DESC),
                    @SortDefault(sort = "updatedAt", direction = Direction.DESC)
            })
            Pageable pageable,
            @RequestParam(required = false) String sourceHubId,
            @RequestParam(required = false) String destinationHubId
    ) {
        return ApiResponse.success(PageResponse.of(
                hubRouteReadService.findHubRoutes(pageable, sourceHubId, destinationHubId)
        ));
    }
}
