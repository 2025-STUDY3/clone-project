package com.ll.clone_coding.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    /// User
    EMAIL_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "USER001", "이미 존재하는 이메일입니다"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "USER002", "비밀번호가 올바르지 않습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER003", "사용자를 찾을 수 없습니다"),

    // Auth
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH001", "인증이 필요합니다"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH002", "유효하지 않은 토큰입니다"),

    // Access
    FORBIDDEN(HttpStatus.FORBIDDEN, "ACCESS001", "접근 권한이 없습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}