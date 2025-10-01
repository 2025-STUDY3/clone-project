package com.ll.clone_coding.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 수정 요청")
public class CommentUpdateRequest {
    @Schema(description = "내용")
    private String content;

    @Schema(description = "익명 여부")
    private boolean anonymous;
}
