package com.example.hanghaespring2.post.dto;

import com.example.hanghaespring2.common.entity.User;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PostDto {
    @Getter
    @Builder
    public static class PostAdd {
        String title;

        String content;

        Long userId;
    }
}
