package com.msa.user.application.service;

import com.msa.user.application.dto.UserDetailResponse;
import com.msa.user.domain.model.User;
import com.msa.user.domain.repository.UserRepository;
import com.msa.user.common.exception.ErrorCode;
import com.msa.user.common.exception.UserException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final HubService hubService;
    private final UserRepository userRepository;

    public UserDetailResponse getUser(final Long userId) {
        User user = getUserOrException(userId);
        return UserDetailResponse.from(user);
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

    private User getUserOrException(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()-> new UserException(ErrorCode.USER_NOT_FOUND));
    }

    private void nameValidation(String username) {
        if(userRepository.existsByUsername(username))
            throw new UserException(ErrorCode.DUPLICATE_USERNAME);
    }
}
