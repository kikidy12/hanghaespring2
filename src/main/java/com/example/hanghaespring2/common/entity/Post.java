package com.example.hanghaespring2.common.entity;

import com.example.hanghaespring2.post.dto.PostDto;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Post extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Builder
    public Post(PostDto.PostAdd dto, User user) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.user = user;
    }
}
