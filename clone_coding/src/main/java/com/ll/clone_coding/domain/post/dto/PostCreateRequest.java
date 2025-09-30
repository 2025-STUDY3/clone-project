package com.ll.clone_coding.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "게시글 작성 요청")
public class PostCreateRequest {

    @Schema(description = "익명 여부")
    private boolean anonymous;
}