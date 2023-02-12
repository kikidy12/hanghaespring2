package com.example.hanghaespring2.post.dto;

import com.example.hanghaespring2.common.entity.Reply;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.reply.dto.ReplyDto;
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
import java.util.stream.Collectors;

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
    public static class PostRes {
        private Long id;
        private String title;
        private String content;
        private String username;
        private List<ReplyDto.ReplyRes> replies;
        private LocalDateTime createdAt;

        @Builder
        public PostRes (Long id, String title, String content, String username, List<Reply> replies, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.username = username;
            this.replies = ObjectUtils.defaultIfNull(replies, new ArrayList<Reply>()).stream().sorted((a, b) ->
                    b.getCreatedAt().compareTo(a.getCreatedAt())
            ).map(Reply::res).collect(Collectors.toList());
            this.createdAt = createdAt;
        }
    }
}
