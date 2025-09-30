package com.ll.clone_coding.domain.user.dto;

import com.ll.clone_coding.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "사용자 프로필")
public class UserProfileResponse {

    @Schema(description = "사용자 ID")
    private Long userId;

    @Schema(description = "이메일")
    private String email;

    @Schema(description = "닉네임")
    private String nickname;

    @Schema(description = "학교")
    private String school;

    public UserProfileResponse(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.school = user.getSchool();
    }
}