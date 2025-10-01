package com.ll.clone_coding.global.init;

import com.ll.clone_coding.domain.category.entitiy.Category;
import com.ll.clone_coding.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(Category.builder().name("자유게시판").build());
            categoryRepository.save(Category.builder().name("비밀게시판").build());
            categoryRepository.save(Category.builder().name("새내기게시판").build());
        }
    }
}
