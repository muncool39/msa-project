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

    private final UserRepository userRepository;

    public UserDetailResponse getUser(final Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->
                new UserException(ErrorCode.USER_NOT_FOUND));
        return UserDetailResponse.from(user);
    }

}
