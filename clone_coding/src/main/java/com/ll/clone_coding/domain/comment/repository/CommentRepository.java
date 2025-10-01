package com.ll.clone_coding.domain.comment.repository;

import com.ll.clone_coding.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    List<Comment> findByPost_PostId(Long postId);
}
