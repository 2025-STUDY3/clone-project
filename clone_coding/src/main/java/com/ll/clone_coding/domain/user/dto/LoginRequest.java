package com.ll.clone_coding.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청")
public class LoginRequest {

    @NotBlank
    @Email
    @Schema(description = "이메일")
    private String email;

    @NotBlank
    @Schema(description = "비밀번호")
    private String password;
}