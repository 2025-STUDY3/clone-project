package com.ll.clone_coding.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "게시글 수정 요청")
public class PostUpdateRequest {

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "익명 여부")
    private boolean anonymous;

    @Schema(description = "카테고리 ID 목록")
    private List<Long> categoryIds;
}
