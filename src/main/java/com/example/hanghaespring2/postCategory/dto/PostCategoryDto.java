package com.example.hanghaespring2.postCategory.dto;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.post.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostCategoryDto {

    @Getter
    public static class PostCategoryAdd {
        private String name;
    }

    @Getter
    public static class PostCategoryUpdate {
        private String name;
    }

    @Getter
    public static class PostCategoryRes {
        private Long id;
        private String name;

        @Builder
        public PostCategoryRes(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    @Getter
    public static class PostCategoryWithPostRes {
        private Long id;
        private String name;
        private List<PostDto.PostRes> postList = new ArrayList<>();

        @Builder
        public PostCategoryWithPostRes(Long id, String name, List<Post> postList) {
            this.id = id;
            this.name = name;
            this.postList = ObjectUtils.defaultIfNull(postList, new ArrayList<Post>()).stream().map(Post::res).collect(Collectors.toList());
        }
    }
}
