package com.example.hanghaespring2.post.dto;

import com.example.hanghaespring2.common.entity.PostCategory;
import com.example.hanghaespring2.common.entity.Reply;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.postCategory.dto.PostCategoryDto;
import com.example.hanghaespring2.reply.dto.ReplyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.util.comparator.Comparators;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class PostDto {
    @Getter
    public static class PostAdd {


        @Schema(defaultValue = "게시글 제목!!")
        String title;

        String content;

        Long categoryId;
    }

    @Getter
    public static class PostUpdate {
        String title;

        String content;
    }


    @Getter
    public static class PostRes {
        private Long id;
        private String title;
        private String content;
        private String username;
        private List<ReplyDto.ReplyRes> replies;
        private Integer likeCount;
        private LocalDateTime createdAt;

        private PostCategoryDto.PostCategoryRes category;


        @Builder
        public PostRes (Long id, String title, String content, String username, List<Reply> replies, PostCategory category, Integer likeCount, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.username = username;
            this.replies = ObjectUtils.defaultIfNull(replies, new ArrayList<Reply>()).stream().map(Reply::res).collect(Collectors.toList());
            this.createdAt = createdAt;
            this.likeCount = likeCount;
            this.category = Optional.ofNullable(category).map(PostCategory::resNoPost).orElse(null);
        }
    }

    @Getter
    public static class PostResNoReply {
        private Long id;
        private String title;
        private String content;
        private String username;

        private PostCategoryDto.PostCategoryRes category;
        private LocalDateTime createdAt;

        @Builder
        public PostResNoReply (Long id, String title, String content, String username, PostCategory category, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.username = username;
            this.category = Optional.ofNullable(category).map(PostCategory::resNoPost).orElse(null);
            this.createdAt = createdAt;
        }
    }
}
