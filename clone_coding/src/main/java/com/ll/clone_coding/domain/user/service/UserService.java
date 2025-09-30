package com.ll.clone_coding.domain.user.service;

import com.ll.clone_coding.domain.user.dto.*;
import com.ll.clone_coding.domain.user.entity.User;
import com.ll.clone_coding.domain.user.repository.UserRepository;
import com.ll.clone_coding.global.exception.CustomException;
import com.ll.clone_coding.global.exception.ErrorCode;
import com.ll.clone_coding.global.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserProfileResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = User.builder()
                .email(request.getEmail())
                .nickname(request.getNickname())
                .school(request.getSchool())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return new UserProfileResponse(userRepository.save(user));
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_PASSWORD));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String token = jwtTokenProvider.createToken(user.getUserId());
        return new LoginResponse(token, user.getUserId(), user.getNickname());
    }

    public UserProfileResponse getMyProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserProfileResponse(user);
    }
}