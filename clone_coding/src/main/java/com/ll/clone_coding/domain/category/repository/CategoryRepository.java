package com.ll.clone_coding.domain.category.repository;

import com.ll.clone_coding.domain.category.entitiy.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
