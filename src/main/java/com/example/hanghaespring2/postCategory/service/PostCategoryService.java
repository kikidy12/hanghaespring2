package com.example.hanghaespring2.postCategory.service;

import com.example.hanghaespring2.postCategory.dto.PostCategoryDto;
import com.example.hanghaespring2.postCategory.repository.PostCategoryRepository;
import com.example.hanghaespring2.common.entity.PostCategory;
import com.example.hanghaespring2.common.util.CustomClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostCategoryService {

    private final PostCategoryRepository postCategoryRepository;

    @Transactional
    public PostCategoryDto.PostCategoryRes addPostCategory(PostCategoryDto.PostCategoryAdd dto) {

        PostCategory postCategory = PostCategory.builder().name(dto.getName()).build();

        return postCategoryRepository.save(postCategory).resNoPost();
    }

    @Transactional
    public PostCategoryDto.PostCategoryRes updatePostCategory(Long id, PostCategoryDto.PostCategoryUpdate dto) {
        PostCategory category = this.postCategoryRepository.findById(id).orElseThrow(() ->
                new CustomClientException("카테고리가 존재하지 않습니다.")
        );

        category.update(dto);

        return category.resNoPost();
    }

    public PostCategoryDto.PostCategoryWithPostRes getPostCategory(Long id) {
        PostCategory category = this.postCategoryRepository.findById(id).orElseThrow(() ->
                new CustomClientException("카테고리가 존재하지 않습니다.")
        );

        return category.res();
    }
}
