package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.post.dto.PostDto;
import com.example.hanghaespring2.postCategory.dto.PostCategoryDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PostCategory extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    List<Post> postList = new ArrayList<>();

    @Builder
    public PostCategory(String name) {
        this.name = name;
    }

    public PostCategoryDto.PostCategoryRes resNoPost() {
        return PostCategoryDto.PostCategoryRes
                .builder()
                .id(this.id)
                .name(this.name)
                .build();
    }

    public PostCategoryDto.PostCategoryWithPostRes res() {
        return PostCategoryDto.PostCategoryWithPostRes
                .builder()
                .id(this.id)
                .name(this.name)
                .postList(this.postList)
                .build();
    }

    public void update(PostCategoryDto.PostCategoryUpdate dto) {
        this.name = ObjectUtils.defaultIfNull(dto.getName(), this.name);
    }
}
