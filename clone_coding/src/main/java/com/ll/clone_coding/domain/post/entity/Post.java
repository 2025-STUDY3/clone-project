package com.ll.clone_coding.domain.post.entity;

import com.ll.clone_coding.domain.category.entitiy.Category;
import com.ll.clone_coding.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @Column(nullable = false)
    private boolean anonymous = false;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PostCategory> postCategories = new LinkedHashSet<>();

    public void addCategory(Category category) {
        this.postCategories.add(PostCategory.of(this, category));
    }

    public void clearCategories() {
        this.postCategories.clear();
    }

    public void update(String title, String content, boolean anonymous) {
        this.title = title;
        this.content = content;
        this.anonymous = anonymous;
    }
}