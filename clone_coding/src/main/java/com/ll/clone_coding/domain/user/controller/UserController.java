package com.ll.clone_coding.domain.user.controller;

import com.ll.clone_coding.domain.user.dto.*;
import com.ll.clone_coding.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "새로운 사용자를 등록합니다. 이메일 중복 확인 후 비밀번호를 암호화하여 저장합니다."
    )
    public ResponseEntity<UserProfileResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.status(CREATED).body(userService.signup(request));
    }

    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = "이메일과 비밀번호로 로그인하여 JWT 토큰을 발급받습니다."
    )
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "로그아웃",
            description = "현재는 토큰을 삭제하는 방식으로 처리됩니다."
    )
    public ResponseEntity<Void> logout() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    @Operation(
            summary = "내 프로필 조회",
            description = "로그인한 사용자의 프로필 정보를 조회합니다. JWT 토큰 필수."
    )
    public ResponseEntity<UserProfileResponse> getMyProfile(@AuthenticationPrincipal Long userId) {
        return ResponseEntity.ok(userService.getMyProfile(userId));
    }
}