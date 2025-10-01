package com.ll.clone_coding.domain.comment.controller;

import com.ll.clone_coding.domain.comment.dto.CommentCreateRequest;
import com.ll.clone_coding.domain.comment.dto.CommentResponse;
import com.ll.clone_coding.domain.comment.dto.CommentUpdateRequest;
import com.ll.clone_coding.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}/comments")
@Tag(name = "Comment API", description = "게시물 댓글 CRUD API")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @Operation(summary = "댓글 작성", description = "특정 게시물 댓글 작성")
    public ResponseEntity<CommentResponse> create(
            @PathVariable Long postId,
            @RequestBody CommentCreateRequest req
    ) {
        CommentResponse response = commentService.create(postId, req);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "댓글 목록 조회", description = "특정 게시물에 달린 모든 댓글 조회")
    public ResponseEntity<List<CommentResponse>> getByPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getByPost(postId));
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "특정 댓글의 내용 수정")
    public ResponseEntity<CommentResponse> update (
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestBody CommentUpdateRequest req
    ) {
        return ResponseEntity.ok(commentService.update(commentId, req));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "특정 댓글 삭제")
    public ResponseEntity<Map<String, Object>> delete (
            @PathVariable Long postId,
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.ok(Map.of(
                "message","삭제 완료",
                "commentId", commentId
        ));
    }
}
