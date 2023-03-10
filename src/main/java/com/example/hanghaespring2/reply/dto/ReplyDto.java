package com.example.hanghaespring2.reply.dto;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ReplyDto {
    @Getter
    public static class ReplyAdd {
        private String message;
        private Long postId;
    }

    @Getter
    public static class ChildReplyAdd {
        private String message;
        private Long parentId;
    }

    @Getter
    public static class UpdateReplyLike {
        private Long replyId;
    }

    @Getter
    public static class ReplyUpdate {
        private String message;
    }

    @Getter
    public static class ReplyRes {
        private Long id;
        private String message;

        private Integer likeCount;

        private List<ReplyRes> children = new ArrayList<>();

        @Builder
        public ReplyRes(Long id, String message, Integer likeCount, List<ReplyRes> children) {
            this.id = id;
            this.message = message;
            this.likeCount = likeCount;
            this.children = children;
        }

        public ReplyRes(Long id, String message) {
            this.id = id;
            this.message = message;
        }
    }
}
