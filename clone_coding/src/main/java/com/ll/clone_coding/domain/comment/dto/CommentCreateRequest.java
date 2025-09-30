package com.ll.clone_coding.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "댓글 작성 요청")
public class CommentCreateRequest {

    @Schema(description = "익명 여부")
    private boolean anonymous;
}