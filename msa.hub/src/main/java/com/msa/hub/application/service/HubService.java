package com.msa.hub.application.service;


import com.msa.hub.application.dto.Role;
import com.msa.hub.domain.model.Hub;
import com.msa.hub.domain.repository.HubRepository;
import com.msa.hub.exception.ErrorCode;
import com.msa.hub.exception.HubException;
import com.msa.hub.presentation.request.HubCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HubService {

    private final UserService userService;
    private final HubRepository hubRepository;

    @Transactional
    public void createHub(final HubCreateRequest request) {
        if(existsHub(request.name())) {
            throw new HubException(ErrorCode.DUPLICATE_HUB_NAME);
        }
        Role managerRole = userService
                .findUser(request.managerId())
                .data().role();

        if(managerRole!=Role.HUB_MANAGER) {
            throw new HubException(ErrorCode.INVALID_ROLE);
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
                        request.longitude(),
                        request.managerId()
                )
        );
    }

    private boolean existsHub(String name) {
        return hubRepository.existsByName(name);
    }

}
