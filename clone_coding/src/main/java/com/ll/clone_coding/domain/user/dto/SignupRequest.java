package com.ll.clone_coding.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청")
public class SignupRequest {

    @NotBlank
    @Email
    @Schema(description = "이메일")
    private String email;

    @NotBlank
    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "학교")
    private String school;

    @NotBlank
    @Schema(description = "비밀번호")
    private String password;
}