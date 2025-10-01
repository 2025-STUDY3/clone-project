package com.ll.clone_coding.domain.post.entity;

import com.ll.clone_coding.domain.category.entitiy.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "post_category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"post_id", "category_id"})
)
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_category_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    public PostCategory(Post post, Category category) {
        this.post = post;
        this.category = category;
    }

    public static PostCategory of(Post post, Category category) {
        return PostCategory.builder()
                .post(post)
                .category(category)
                .build();
    }
}
