package com.ll.clone_coding.domain.post.dto;

import com.ll.clone_coding.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostDetailResponse {
    private Long postId;
    private String nickname;  // 익명이면 "익명", 아니면 user.nickname
    private boolean anonymous;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private List<CategorySimple> categories;

    @Getter
    @Builder
    public static class CategorySimple {
        private Long id;
        private String name;
    }

    public static PostDetailResponse of(Post post, String displayNickname, List<CategorySimple> categories) {
        return PostDetailResponse.builder()
                .postId(post.getPostId())
                .nickname(displayNickname)
                .anonymous(post.isAnonymous())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .categories(categories)
                .build();
    }
}