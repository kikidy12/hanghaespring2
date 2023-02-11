package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.post.dto.PostDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void setUser(User user) {
        if(this.user != null) {
            this.user.getPostList().remove(this);
        }
        this.user = user;
        if(!user.getPostList().contains(this)) {
            user.addPost(this);
        }
    }


    @Builder
    public Post(PostDto.PostAdd dto, User user) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        setUser(user);
    }

    public PostDto.PostRes res () {
        return PostDto.PostRes
                .builder()
                .id(this.id)
                .content(this.content)
                .title(this.title)
                .username(this.user.getUsername())
                .createdAt(this.getCreatedAt())
                .build();
    }

    public void update(PostDto.PostUpdate dto) {
        this.title = ObjectUtils.defaultIfNull(dto.getTitle(), this.title);

        this.content = ObjectUtils.defaultIfNull(dto.getContent(), this.content);
    }
}
