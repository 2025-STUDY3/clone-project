package com.ll.clone_coding.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "로그인 응답")
public class LoginResponse {

    @Schema(description = "JWT 토큰")
    private String accessToken;

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "닉네임")
    private String nickname;
}