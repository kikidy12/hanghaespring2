package com.example.hanghaespring2.postCategory.controller;

import com.example.hanghaespring2.common.entity.UserRoleEnum;
import com.example.hanghaespring2.postCategory.dto.PostCategoryDto;
import com.example.hanghaespring2.postCategory.service.PostCategoryService;
import com.example.hanghaespring2.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


@Tag(name = "category")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class PostCategoryController {

    private final PostCategoryService postCategoryService;

    @Secured("ROLE_ADMIN")
    @PostMapping("/")
    public ResponseDto<PostCategoryDto.PostCategoryRes> addPostCategory(@RequestBody PostCategoryDto.PostCategoryAdd dto) {

        System.out.println(dto.getName());
        return ResponseDto.of(HttpStatus.OK, "등록 성공", postCategoryService.addPostCategory(dto));
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseDto<PostCategoryDto.PostCategoryRes> updatePostCategory(
            @PathVariable Long id,
            @RequestBody PostCategoryDto.PostCategoryUpdate dto
    ) {

        System.out.println(dto.getName());
        return ResponseDto.of(HttpStatus.OK, "수정 성공", postCategoryService.updatePostCategory(id, dto));
    }


    @GetMapping("/{id}")
    @SecurityRequirements()
    public ResponseDto<PostCategoryDto.PostCategoryWithPostRes> getPostCategory(@PathVariable Long id) {
        return ResponseDto.of(HttpStatus.OK, "조회 성공", postCategoryService.getPostCategory(id));
    }

}
