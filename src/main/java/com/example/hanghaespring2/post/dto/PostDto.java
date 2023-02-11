package com.example.hanghaespring2.post.dto;

import com.example.hanghaespring2.common.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class PostDto {
    @Getter
    @AllArgsConstructor
    public static class PostAdd {
        String title;

        String content;
    }

    @Getter
    @AllArgsConstructor
    public static class PostUpdate {
        String title;

        String content;
    }


    @Getter
    @Builder
    public static class PostRes {
        Long id;
        String title;
        String content;
        String username;

        LocalDateTime createdAt;
    }
}
