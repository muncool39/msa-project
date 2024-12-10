package com.msa.user.application.service;


import com.msa.user.domain.model.User;
import com.msa.user.domain.repository.UserRepository;
import com.msa.user.exception.ErrorCode;
import com.msa.user.exception.UserException;
import com.msa.user.presentation.request.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void createUser(SignUpRequest request) {
        if(existsUser(request.username())) {
            throw new UserException(ErrorCode.DUPLICATE_USERNAME);
        }
        userRepository.save(
                User.createBy(
                        request.username(),
                        passwordEncoder.encode(request.password()),
                        request.email(),
                        request.slackId(),
                        request.role()
                )
        );
    }

    private boolean existsUser(String username) {
        return userRepository.existsByUsername(username);
    }
}
