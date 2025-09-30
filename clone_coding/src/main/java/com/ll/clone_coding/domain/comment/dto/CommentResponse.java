package com.ll.clone_coding.domain.comment.dto;

import com.ll.clone_coding.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
    private Long commentId;
    private String nickname;  // 익명이면 "익명", 아니면 user.nickname
    private boolean anonymous;

    public static CommentResponse of(Comment comment, String displayNickname) {
        return CommentResponse.builder()
                .commentId(comment.getCommentId())
                .nickname(displayNickname)
                .anonymous(comment.isAnonymous())
                .build();
    }
}