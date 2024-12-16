package com.msa.user.application.service;

import com.msa.user.application.dto.UserDetailResponse;
import com.msa.user.application.dto.UserBasicResponse;
import com.msa.user.domain.model.Role;
import com.msa.user.domain.model.User;
import com.msa.user.domain.repository.UserRepository;
import com.msa.user.common.exception.ErrorCode;
import com.msa.user.common.exception.UserException;
import com.msa.user.presentation.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final HubService hubService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserDetailResponse getUser(final Long userId) {
        User user = getUserOrException(userId);
        return UserDetailResponse.from(user);
    }

    public Page<UserBasicResponse> findUsers(
            Pageable pageable, String username, Role role, String belongHudId, String belongCompanyId
    ) {
        return userRepository
                .findUsersWith(pageable, username, role, belongHudId, belongCompanyId)
                .map(UserBasicResponse::toUserPageResponse);
    }

    @Transactional
    public void updateUser(final Long userId, final UserUpdateRequest request) {
        User user = getUserOrException(userId);
        if(request.username() != null) {
            nameValidation(request.username());
        }
        user.update(
                request.username(),
                (request.password()==null) ? null
                        : passwordEncoder.encode(request.password()),
                request.email(),
                request.slackId()
        );
    }

    @Transactional
    public void setBelongHub(final Long userId, final String hubId) {
        User user = getUserOrException(userId);
        if (!hubService.verifyHub(hubId)) {
            throw new UserException(ErrorCode.INVALID_HUB);
        }
        user.setBelongHub(hubId);
        hubService.postManager(hubId, userId);
    }

    @Transactional
    public void deleteUser(final Long userId) {
        User user = getUserOrException(userId);
        user.deleteBase(userId);
    }

    private User getUserOrException(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    private void nameValidation(String username) {
        if(userRepository.existsByUsername(username))
            throw new UserException(ErrorCode.DUPLICATE_USERNAME);
    }
}
