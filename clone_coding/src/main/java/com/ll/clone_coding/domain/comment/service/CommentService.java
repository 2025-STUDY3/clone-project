package com.ll.clone_coding.domain.comment.service;

import com.ll.clone_coding.domain.anonymous.service.AnonymousService;
import com.ll.clone_coding.domain.comment.dto.CommentCreateRequest;
import com.ll.clone_coding.domain.comment.dto.CommentResponse;
import com.ll.clone_coding.domain.comment.dto.CommentUpdateRequest;
import com.ll.clone_coding.domain.comment.entity.Comment;
import com.ll.clone_coding.domain.comment.repository.CommentRepository;
import com.ll.clone_coding.domain.post.entity.Post;
import com.ll.clone_coding.domain.post.repository.PostRepository;
import com.ll.clone_coding.domain.user.entity.User;
import com.ll.clone_coding.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AnonymousService anonymousService;

    private Long currentUserIdOrThrow() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getPrincipal() == null)
            throw new IllegalStateException("로그인 필요");

        if (!(auth.getPrincipal() instanceof Long userId))
            throw new IllegalStateException("인증정보 오류");

        return userId;
    }

    @Transactional
    public CommentResponse create(Long postId, CommentCreateRequest req) {
        Long userId = currentUserIdOrThrow();
        User user = userRepository.findById(userId).orElseThrow();
        Post post = postRepository.findById(postId).orElseThrow();

        Comment comment = Comment.builder()
                .user(user)
                .post(post)
                .content(req.getContent())
                .anonymous(req.isAnonymous())
                .build();

        Comment saved = commentRepository.save(comment);

        String nickname = anonymousService.getDisplayNickname(user, saved.isAnonymous());
        return CommentResponse.of(saved, nickname);
    }

    public List<CommentResponse> getByPost(Long postId) {
        List<Comment> comments = commentRepository.findByPost_PostId(postId);

        return comments.stream()
                .map(c -> {
                    String nick = anonymousService.getDisplayNickname(c.getUser(), c.isAnonymous());
                    return CommentResponse.of(c, nick);
                })
                .toList();
    }

    @Transactional
    public CommentResponse update(Long commentId, CommentUpdateRequest req) {
        Long userId = currentUserIdOrThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if (!Objects.equals(comment.getUser().getUserId(), userId))
            throw new SecurityException("작성자만 수정 가능");

        comment = Comment.builder()
                .commentId(comment.getCommentId())
                .user(comment.getUser())
                .post(comment.getPost())
                .content(req.getContent())
                .anonymous(req.isAnonymous())
                .createdAt(comment.getCreatedAt())
                .build();

        Comment saved = commentRepository.save(comment);

        String nick = anonymousService.getDisplayNickname(saved.getUser(), saved.isAnonymous());
        return CommentResponse.of(saved, nick);
    }

    @Transactional
    public void delete(Long commentId) {
        Long userId = currentUserIdOrThrow();
        Comment comment = commentRepository.findById(commentId).orElseThrow();

        if (!Objects.equals(comment.getUser().getUserId(), userId))
            throw new SecurityException("작성자만 삭제 가능");

        commentRepository.delete(comment);
    }
}
