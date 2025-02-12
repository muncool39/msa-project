package com.msa.hub.application.service;


import com.msa.hub.application.dto.HubBasicResponse;
import com.msa.hub.application.dto.HubDetailResponse;
import com.msa.hub.domain.model.Hub;
import com.msa.hub.domain.model.HubRoute;
import com.msa.hub.domain.repository.HubRepository;
import com.msa.hub.common.exception.ErrorCode;
import com.msa.hub.common.exception.HubException;
import com.msa.hub.presentation.request.HubCreateRequest;
import com.msa.hub.presentation.request.HubUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final HubRepository hubRepository;
    private final UserService userService;

    @Transactional
    @CacheEvict(cacheNames = "hub_list_cache", allEntries = true)
    public void createHub(final HubCreateRequest request) {
        if(existsHub(request.name())) {
            throw new HubException(ErrorCode.DUPLICATE_HUB_NAME);
        }
        hubRepository.save(
                Hub.createBy(
                        request.name(),
                        request.city(),
                        request.district(),
                        request.streetName(),
                        request.streetNumber(),
                        request.addressDetail(),
                        request.latitude(),
                        request.longitude()
                )
        );
    }

    public HubDetailResponse getHubDetail(final String id) {
        return HubDetailResponse.from(
                hubRepository.findByIdAndIsDeleted(id, false)
                        .orElseThrow(()->new HubException(ErrorCode.HUB_NOT_FOUND))
        );
    }

    @Cacheable(cacheNames = "hub_list_cache")
    public Page<HubBasicResponse> findHubs(
            Pageable pageable, String name, String city, String district, String streetName
    ) {
        return hubRepository
                .findHubsWith(pageable, name, city, district, streetName)
                .map(HubBasicResponse::fromEntity);
    }

    @Transactional
    @CacheEvict(cacheNames = "hub_list_cache", allEntries = true)
    public void updateHub(final String hubId, final HubUpdateRequest request) {
        Hub hub = getHubOrException(hubId);
        hub.update(
                request.name(), request.city(),
                request.district(), request.streetName(), request.streetNumber(), request.addressDetail(),
                request.latitude(), request.longitude()
        );
    }

    @Transactional
    public void updateHubManager(final String hubId, final Long userId) {
        Hub hub = getHubOrException(hubId);
        hub.setManager(userId);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = "hub_list_cache", allEntries = true),
            @CacheEvict(cacheNames = "hub_routes_cache", allEntries = true)
    })
    public void deleteHub(final String hubId, final Long userId) {
        Hub hub = getHubOrException(hubId);
        if(hub.getManagerId() != null) {
            userService.detachBelongHub(hub.getManagerId());
        }
        hub.softDeleteHub(userId);
    }

    private Hub getHubOrException(final String hubId) {
        return hubRepository.findById(hubId)
                .orElseThrow(()->new HubException(ErrorCode.HUB_NOT_FOUND));
    }

    private boolean existsHub(String name) {
        return hubRepository.existsByName(name);
    }

}
