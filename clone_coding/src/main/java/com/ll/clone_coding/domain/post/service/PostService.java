package com.ll.clone_coding.domain.post.service;

import com.ll.clone_coding.domain.anonymous.service.AnonymousService;
import com.ll.clone_coding.domain.category.entitiy.Category;
import com.ll.clone_coding.domain.category.repository.CategoryRepository;
import com.ll.clone_coding.domain.post.dto.PostCreateRequest;
import com.ll.clone_coding.domain.post.dto.PostDetailResponse;
import com.ll.clone_coding.domain.post.dto.PostUpdateRequest;
import com.ll.clone_coding.domain.post.entity.Post;
import com.ll.clone_coding.domain.post.entity.PostCategory;
import com.ll.clone_coding.domain.post.repository.PostRepository;
import com.ll.clone_coding.domain.user.entity.User;
import com.ll.clone_coding.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
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
    public Long create(PostCreateRequest req) {
        Long userId = currentUserIdOrThrow();
        User writer = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Post post = Post.builder()
                .user(writer)
                .title(req.getTitle())
                .content(req.getContent())
                .anonymous(req.isAnonymous())
                .build();

        if (req.getCategoryIds() != null) {
            List<Category> categories = categoryRepository.findAllById(req.getCategoryIds());
            categories.forEach(post::addCategory);
        }

        Post saved = postRepository.save(post);
        return saved.getPostId();
    }

    public PostDetailResponse get(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("게시글 없음"));
        String displayNickname = anonymousService.getDisplayNickname(post.getUser(), post.isAnonymous());

        List<PostDetailResponse.CategorySimple> cats = post.getPostCategories().stream()
                .map(PostCategory::getCategory)
                .map(c -> PostDetailResponse.CategorySimple.builder().id(c.getId()).name(c.getName()).build())
                .toList();

        return PostDetailResponse.of(post, displayNickname, cats);
    }

    @Transactional
    public void update(Long postId, PostUpdateRequest req) {
        Long userId = currentUserIdOrThrow();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("게시글 없음"));
        if (!Objects.equals(post.getUser().getUserId(), userId))
            throw new SecurityException("작성자만 수정 가능");

        post.update(req.getTitle(), req.getContent(), req.isAnonymous());

        post.clearCategories();
        if (req.getCategoryIds() != null && !req.getCategoryIds().isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(req.getCategoryIds());
            categories.forEach(post::addCategory);
        }
    }

    @Transactional
    public void delete(Long postId) {
        Long userId = currentUserIdOrThrow();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("게시글 없음"));

        if (!Objects.equals(post.getUser().getUserId(), userId))
            throw new SecurityException("작성자만 삭제 가능");

        postRepository.delete(post);
    }

    public List<PostDetailResponse> listAll() {
        return postRepository.findAllWithCategories().stream()
                .map(p -> {
                    String nick = anonymousService.getDisplayNickname(p.getUser(), p.isAnonymous());

                    List<PostDetailResponse.CategorySimple> categories =
                            p.getPostCategories().stream()
                                    .map(pc -> pc.getCategory())
                                    .map(c -> PostDetailResponse.CategorySimple.builder()
                                            .id(c.getId())
                                            .name(c.getName())
                                            .build())
                                    .toList();

                    return PostDetailResponse.of(p, nick, categories);
                })
                .toList();
    }
}
