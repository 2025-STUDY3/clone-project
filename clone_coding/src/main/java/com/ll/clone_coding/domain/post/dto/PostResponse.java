package com.ll.clone_coding.domain.post.dto;

import com.ll.clone_coding.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private Long postId;
    private String nickname;  // 익명이면 "익명", 아니면 user.nickname
    private boolean anonymous;

    public static PostResponse of(Post post, String displayNickname) {
        return PostResponse.builder()
                .postId(post.getPostId())
                .nickname(displayNickname)
                .anonymous(post.isAnonymous())
                .build();
    }
}