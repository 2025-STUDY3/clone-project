package com.ll.clone_coding.domain.post.controller;

import com.ll.clone_coding.domain.post.dto.PostCreateRequest;
import com.ll.clone_coding.domain.post.dto.PostDetailResponse;
import com.ll.clone_coding.domain.post.dto.PostUpdateRequest;
import com.ll.clone_coding.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@Tag(name = "Post API", description = "게시물 CRUD API")
public class PostController {

    private final PostService postService;

    @PostMapping
    @Operation(summary = "게시물 생성", description = "새로운 게시물 생성")
    public ResponseEntity<PostDetailResponse> create(@RequestBody PostCreateRequest req) {
        Long id = postService.create(req);
        PostDetailResponse response = postService.get(id); // 방금 생성된 글 다시 조회
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시물 단건 조회", description = "게시물 ID로 게시물 조회")
    public ResponseEntity<PostDetailResponse> get(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.get(postId));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시물 수정", description = "기존 게시물 내용 수정")
    public ResponseEntity<PostDetailResponse> update(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequest req
    ) {
        postService.update(postId, req);
        PostDetailResponse response = postService.get(postId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시물 삭제", description = "게시물 ID로 게시물 삭제")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long postId) {
        postService.delete(postId);
        return ResponseEntity.ok(Map.of(
                "message", "삭제 완료",
                "postId", postId
        ));
    }

    @GetMapping
    @Operation(summary = "게시물 전체 조회", description = "등록된 모든 게시물 조회")
    public ResponseEntity<List<PostDetailResponse>> list() {
        return ResponseEntity.ok(postService.listAll());
    }
}
