package com.ll.clone_coding.domain.post.repository;

import com.ll.clone_coding.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
       select distinct p
       from Post p
       left join fetch p.postCategories pc
       left join fetch pc.category c
       """)
    List<Post> findAllWithCategories();

}
